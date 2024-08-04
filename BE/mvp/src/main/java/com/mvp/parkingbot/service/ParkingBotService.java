package com.mvp.parkingbot.service;

import com.mvp.common.exception.RestApiException;
import com.mvp.common.exception.StatusCode;
import com.mvp.logger.dto.EntranceLogDTO;
import com.mvp.logger.dto.ExitLogDTO;
import com.mvp.logger.service.LoggerService;
import com.mvp.parkingbot.converter.ParkingBotConverter;
import com.mvp.parkingbot.dto.*;
import com.mvp.parkingbot.entity.ParkingBot;
import com.mvp.parkingbot.repository.ParkingBotRepository;
import com.mvp.stats.service.StatsService;
import com.mvp.utils.TaskQueue;
import com.mvp.vehicle.converter.ParkedVehicleConverter;
import com.mvp.vehicle.entity.ParkedVehicle;
import com.mvp.vehicle.entity.ParkingLotSpot;
import com.mvp.vehicle.repository.ParkedVehicleRepository;
import com.mvp.vehicle.repository.ParkingLotSpotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
// Parkingbotservice
public class ParkingBotService {
    // TODO : enum으로 변경
    private final int BOT_IDLE = 0;
    private final int BOT_BUSY = 1;
    private final int LOT_EMPTY = 0;
    private final int LOT_OCCUPIED = 1;

    private final ParkingLotSpotRepository parkingLotSpotRepository;
    private final ParkingBotRepository parkingBotRepository;
    private final ParkedVehicleRepository parkedVehicleRepository;
    private final LoggerService LoggerService;
    private final StatsService statsService;
    private TaskQueue taskQueue;

    /**
     * 입차 처리
     * @param enterRequestDTO
     * @return
     */
    public Task handleEnterRequest(EnterRequestDTO enterRequestDTO) {
        // 주차공간 확인
        Optional<ParkingLotSpot> availableSpot = parkingLotSpotRepository.findFirstByStatus(LOT_EMPTY);

        if (availableSpot.isEmpty()) {
            throw new RuntimeException("주차장에 공간이 없습니다!!");
        }

        // 차량 정보 등록
        ParkedVehicle vehicle = ParkedVehicle.builder()
                .licensePlate(enterRequestDTO.getLicensePlate())
                .entranceTime(enterRequestDTO.getEntranceTime())
                .image(enterRequestDTO.getImage())
                .build();
        parkedVehicleRepository.save(vehicle);

        // 주차공간 상태변화
        ParkingLotSpot spot = availableSpot.get();
        spot = ParkingLotSpot.builder()
                .id(spot.getId())
                .spotNumber(spot.getSpotNumber())
                .parkedVehicle(vehicle)
                .status(LOT_OCCUPIED)
                .build();
        parkingLotSpotRepository.save(spot);

        // 주차봇 할당
        Optional<ParkingBot> availableBot = parkingBotRepository.findFirstByStatus(BOT_IDLE);
        Task task;
        if (availableBot.isEmpty()) {
            task = Task.builder()
                    .parkingBotSerialNumber(null)
                    .start(0)
                    .end(spot.getSpotNumber())
                    .build();
            taskQueue.addWaitingTask(task);
        } else{
            task = Task.builder()
                    .parkingBotSerialNumber(availableBot.get().getSerialNumber())
                    .start(0)
                    .end(spot.getSpotNumber())
                    .build();
            taskQueue.addTask(task);
        }

        EntranceLogDTO logDto = EntranceLogDTO.builder()
                .licensePlate(enterRequestDTO.getLicensePlate())
                .image(enterRequestDTO.getImage())
                .entranceTime(enterRequestDTO.getEntranceTime())
                .build();
        LoggerService.createEntryLog(logDto);

        return task;
    }

    /**
     * 출차 처리
     * @param licensePlate
     * @return
     */
    public boolean handleExitRequest(String licensePlate) {
        // 차량 정보 확인
        ParkedVehicle parkedVehicle = parkedVehicleRepository.findByLicensePlate(licensePlate);
        if(parkedVehicle == null){
            return false;
        }

        // 주차공간 상태변화
        ParkingLotSpot spot = parkingLotSpotRepository.findByParkedVehicleId(parkedVehicle.getId());
        if(spot != null){
            spot = ParkingLotSpot.builder()
                    .id(spot.getId())
                    .spotNumber(spot.getSpotNumber())
                    .parkedVehicle(null)
                    .status(LOT_EMPTY)
                    .build();
            parkingLotSpotRepository.save(spot);
        }

        // 차량 정보 삭제
        parkedVehicleRepository.delete(parkedVehicle);
        // 주차봇 할당
        Optional<ParkingBot> availableBot = parkingBotRepository.findFirstByStatus(BOT_IDLE);

        // 작업 추가
        Task task;
        if (availableBot.isEmpty()) {
            task = Task.builder()
                    .parkingBotSerialNumber(null)
                    .start(spot != null ? spot.getSpotNumber() : null)
                    .end(1)
                    .build();
            taskQueue.addWaitingTask(task);
        } else{
            task = Task.builder()
                    .parkingBotSerialNumber(availableBot.get().getSerialNumber())
                    .start(spot != null ? spot.getSpotNumber() : null)
                    .end(1)
                    .build();
            taskQueue.addTask(task);
        }

        int price = statsService.calculatePrice(ParkedVehicleConverter.entityToDto(parkedVehicle));
        ExitLogDTO logDto = ExitLogDTO.builder()
                .licensePlate(parkedVehicle.getLicensePlate())
                .image(parkedVehicle.getImage())
                .fee(price)
                .build();
        LoggerService.createExitLog(logDto);

        return true;
    }

    // 주차봇에게 할당된 작업을 가져옴
    public Task getTaskfromQueue() {
        return taskQueue.getNextTask();
    }

    /**
     * 임의 이동
     * @param moveRequestDTO
     * @return
     */
    public Task handleMoveRequest(MoveRequestDTO moveRequestDTO) {
        int start = moveRequestDTO.getStart();
        int end = moveRequestDTO.getEnd();

        ParkingLotSpot startSpot = parkingLotSpotRepository.findBySpotNumber(start);
        ParkingLotSpot endSpot = parkingLotSpotRepository.findBySpotNumber(end);

        // 주차공간 확인
        if(startSpot == null || endSpot == null){
            return null;
        }
        if(startSpot.getParkedVehicle() == null){
            return null;
        }
        if(endSpot.getParkedVehicle() != null){
            return null;
        }

        // 주차공간 상태변화
        ParkedVehicle vehicle = startSpot.getParkedVehicle();
        startSpot = ParkingLotSpot.builder()
                .id(startSpot.getId())
                .spotNumber(startSpot.getSpotNumber())
                .parkedVehicle(null)
                .status(LOT_EMPTY)
                .build();

        endSpot = ParkingLotSpot.builder()
                .id(endSpot.getId())
                .spotNumber(endSpot.getSpotNumber())
                .parkedVehicle(vehicle)
                .status(LOT_OCCUPIED)
                .build();

        parkingLotSpotRepository.save(startSpot);
        parkingLotSpotRepository.save(endSpot);

        // 주차봇 할당
        ParkingBot availableBot = parkingBotRepository.findFirstByStatus(BOT_IDLE).orElse(null);
        Task task = Task.builder()
                .parkingBotSerialNumber(availableBot != null ? availableBot.getSerialNumber() : null)
                .start(start)
                .end(end)
                .build();

        // 작업 추가
        if(availableBot != null){
            taskQueue.addTask(task);
        } else{
            taskQueue.addWaitingTask(task);
        }

        return task;
    }

    /**
     * 차량 위치 변경 handleMoveRequest을 사용 
     * moveVehicleRequestDTO -> MoveRequestDTO로 변환한 뒤 handleMoveRequest 사용
     * @param moveVehicleRequestDTO
     * @return
     */
    public Task handleMoveVehicleRequest(MoveVehicleRequestDTO moveVehicleRequestDTO) {
        //MoveVehicleRequestDTO -> moveRequestDTO
        ParkingLotSpot parkingLotSpot = parkingLotSpotRepository.findByParkedVehicle_LicensePlate(moveVehicleRequestDTO.getLicensePlate());

        if(parkingLotSpot == null || parkingLotSpot.getId() == null){
            throw new RestApiException(StatusCode.NO_SUCH_ELEMENT);
        }

        ParkedVehicle parkedVehicle = parkingLotSpot.getParkedVehicle();

        if(parkedVehicle == null || parkedVehicle.getId() == null){
            throw new RestApiException(StatusCode.NO_SUCH_ELEMENT);
        }

        MoveRequestDTO dto = MoveRequestDTO.builder()
                .start(parkingLotSpot.getSpotNumber())
                .end(moveVehicleRequestDTO.getEndSpot())
                .build();

        return handleMoveRequest(dto);
    }

    /**
     * 주차봇 상태 변경
     * @param serialNumber, status
     * @return
     */
    public ParkingBotDTO updateStatus(Integer serialNumber, Integer status) {
        ParkingBot parkingBot = parkingBotRepository.findBySerialNumber(serialNumber);

        parkingBot = ParkingBot.builder()
                .serialNumber(serialNumber)
                .status(status)
                .build();
        parkingBotRepository.save(parkingBot);

        return ParkingBotConverter.entityToDto(parkingBot);
    }

    /**
     * 주차봇 전체 상태 변경
     * @param status
     * @return
     */
    public List<ParkingBotDTO> updateAllStatus(Integer status) {
        List<ParkingBot> parkingBotList = parkingBotRepository.findAll();

        for (ParkingBot parkingBot : parkingBotList) {
            parkingBot = ParkingBot.builder()
                    .serialNumber(parkingBot.getSerialNumber())
                    .status(status)
                    .build();
            parkingBotRepository.save(parkingBot);
        }

        return ParkingBotConverter.entityToDtoList(parkingBotList);
    }

    /**
     * 주차봇 생성
     * @param parkingBotDTO
     * @return
     */
    public ParkingBotDTO createParkingBot(ParkingBotDTO parkingBotDTO) {
        ParkingBot parkingBot = ParkingBot.builder()
                .serialNumber(parkingBotDTO.getSerialNumber())
                .status(BOT_IDLE)
                .build();
        parkingBotRepository.save(parkingBot);

        return ParkingBotConverter.entityToDto(parkingBot);
    }

    /**
     * 주차봇 삭제
     * @param serialNumber
     * @return
     */
    public boolean deleteParkingBot(Integer serialNumber) {
        ParkingBot parkingBot = parkingBotRepository.findBySerialNumber(serialNumber);
        if(parkingBot == null){
            return false;
        }

        parkingBotRepository.delete(parkingBot);
        return true;
    }

    /**
     * 주차봇 리스트 조회
     * @return
     */
    public List<ParkingBotDTO> getParkingBotList() {
        List<ParkingBot> parkingBotList = parkingBotRepository.findAll();
        return ParkingBotConverter.entityToDtoList(parkingBotList);
    }


}
