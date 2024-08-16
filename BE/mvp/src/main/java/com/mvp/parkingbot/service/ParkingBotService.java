package com.mvp.parkingbot.service;

import com.mvp.calculator.service.CalculatorService;
import com.mvp.common.exception.RestApiException;
import com.mvp.common.exception.StatusCode;
import com.mvp.logger.dto.EntranceLogDTO;
import com.mvp.logger.dto.ExitLogDTO;
import com.mvp.logger.service.LoggerService;
import com.mvp.membership.service.MembershipService;
import com.mvp.parkingbot.converter.ParkingBotConverter;
import com.mvp.parkingbot.dto.*;
import com.mvp.parkingbot.entity.ParkingBot;
import com.mvp.parkingbot.repository.ParkingBotRepository;
import com.mvp.stats.service.StatsService;
import com.mvp.task.dto.Task;
import com.mvp.task.service.TaskService;
import com.mvp.vehicle.converter.ParkedVehicleConverter;
import com.mvp.vehicle.entity.ParkedVehicle;
import com.mvp.vehicle.entity.ParkingLotSpot;
import com.mvp.vehicle.repository.ParkedVehicleRepository;
import com.mvp.vehicle.repository.ParkingLotSpotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ParkingBotService {
    // TODO : enum으로 변경
    private final int BOT_IDLE = 0;
    private final int BOT_BUSY = 1;

    private final int LOT_EMPTY = 0;
    private final int LOT_OCCUPIED = 1;
    private final int LOT_IMPOSSIBLE = 2;

    private final int VEHICLE_PARKED = 0;
    private final int VEHICLE_WAIT = 1;
    private final int VEHICLE_MOVE = 2;

    private final int ENTRANCE = 0;
    private final int EXIT = 1;
    private final int MOVE = 2;

    private final ParkingLotSpotRepository parkingLotSpotRepository;
    private final ParkingBotRepository parkingBotRepository;
    private final ParkedVehicleRepository parkedVehicleRepository;
    private final LoggerService LoggerService;
    private final StatsService statsService;
    private final MembershipService membershipService;
    private final CalculatorService calculatorService;
    private final TaskService taskService;

    /**
     * 입차 처리
     * @param enterRequestDTO
     * @return
     */
    @Transactional
    public Task handleEnterRequest(EnterRequestDTO enterRequestDTO) {
        // 주차공간 확인
        Optional<ParkingLotSpot> availableSpot = parkingLotSpotRepository.findFirstByStatus(LOT_EMPTY);

        if (availableSpot.isEmpty()) {
            throw new RestApiException(StatusCode.NO_AVAILABLE_LOT);
        }

        // 차량 정보 등록
        ParkedVehicle vehicle = ParkedVehicle.builder()
                .licensePlate(enterRequestDTO.getLicensePlate())
                .entranceTime(enterRequestDTO.getEntranceTime())
                .image(enterRequestDTO.getImage())
                .status(VEHICLE_WAIT)
                .build();
        parkedVehicleRepository.save(vehicle);

        // 주차공간 상태변화
        ParkingLotSpot spot = availableSpot.get();
        spot.updateVehicleAndStatus(vehicle,LOT_IMPOSSIBLE);


        // 주차봇 할당
        Optional<ParkingBot> availableBot = parkingBotRepository.findFirstByStatus(BOT_IDLE);
        Task task;
        if (availableBot.isEmpty()) {
            // 대기열에 추가
            task = Task.builder()
                    .parkedVehicleId(vehicle.getId())
                    .parkingBotSerialNumber(null)
                    .start(0)
                    .end(spot.getSpotNumber())
                    .type(ENTRANCE)
                    .build();
            taskService.addWaitingTask(task);
        } else{
            availableBot.get().updateStatus(BOT_BUSY);
            vehicle.updateStatus(VEHICLE_MOVE);
            // 바로 전송
            task = Task.builder()
                    .parkingBotSerialNumber(availableBot.get().getSerialNumber())
                    .parkedVehicleId(vehicle.getId())
                    .start(0)
                    .end(spot.getSpotNumber())
                    .type(ENTRANCE)
                    .build();
            taskService.sendMessage(task);
        }

        // 로그 추가
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
    @Transactional
    public boolean handleExitRequest(String licensePlate) {
        // 차량 정보 확인
        ParkedVehicle parkedVehicle = parkedVehicleRepository.findByLicensePlate(licensePlate);
        if(parkedVehicle == null){
            throw new RestApiException(StatusCode.NO_TARGET_VEHICLE);
        }

        // 차량 상태변화
        parkedVehicle.updateStatus(VEHICLE_WAIT);

        // 주차공간 상태변화
        ParkingLotSpot spot = parkingLotSpotRepository.findByParkedVehicleId(parkedVehicle.getId());
        if(spot == null){
            throw new RestApiException(StatusCode.NO_ALLOCATED_LOT);
        }
        spot.updateVehicleAndStatus(spot.getParkedVehicle(),LOT_IMPOSSIBLE);

        // 주차봇 할당
        Optional<ParkingBot> availableBot = parkingBotRepository.findFirstByStatus(BOT_IDLE);

        // 작업 추가
        Task task;
        if (availableBot.isEmpty()) {
            task = Task.builder()
                    .parkedVehicleId(parkedVehicle.getId())
                    .parkingBotSerialNumber(null)
                    .start(spot.getSpotNumber())
                    .end(0)
                    .type(EXIT)
                    .build();
            taskService.addWaitingTask(task);
        } else{
            availableBot.get().updateStatus(BOT_BUSY);
            parkedVehicle.updateStatus(VEHICLE_MOVE);
            task = Task.builder()
                    .parkedVehicleId(parkedVehicle.getId())
                    .parkingBotSerialNumber(availableBot.get().getSerialNumber())
                    .start(spot.getSpotNumber())
                    .end(0)
                    .type(EXIT)
                    .build();
            taskService.sendMessage(task);
        }

        long price = calculatorService.calculatePrice(ParkedVehicleConverter.entityToDto(parkedVehicle));

        ExitLogDTO logDto = ExitLogDTO.builder()
                .licensePlate(parkedVehicle.getLicensePlate())
                .image(parkedVehicle.getImage())
                .fee(price)
                .entranceTime(parkedVehicle.getEntranceTime())
                .build();
        LoggerService.createExitLog(logDto);

        return true;
    }

    /**
     * 임의 이동
     * @param moveRequestDTO
     * @return
     */
    @Transactional
    public Task handleMoveRequest(MoveRequestDTO moveRequestDTO) {
        int start = moveRequestDTO.getStart();
        int end = moveRequestDTO.getEnd();

        ParkingLotSpot startSpot = parkingLotSpotRepository.findBySpotNumber(start);
        ParkingLotSpot endSpot = parkingLotSpotRepository.findBySpotNumber(end);

        // 주차공간 확인
        if(startSpot == null || endSpot == null){
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }
        if(startSpot.getParkedVehicle() == null){
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }
        if(endSpot.getParkedVehicle() != null){
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }

        // 주차공간 상태변화
        ParkedVehicle parkedVehicle = startSpot.getParkedVehicle();
        parkedVehicle.updateStatus(VEHICLE_WAIT);
        startSpot.updateVehicleAndStatus(null,LOT_IMPOSSIBLE);
        endSpot.updateVehicleAndStatus(parkedVehicle,LOT_OCCUPIED);

        // 주차봇 할당
        ParkingBot availableBot = parkingBotRepository.findFirstByStatus(BOT_IDLE).orElse(null);
        Task task = Task.builder()
                .parkedVehicleId(parkedVehicle.getId())
                .parkingBotSerialNumber(availableBot != null ? availableBot.getSerialNumber() : null)
                .start(start)
                .end(end)
                .type(MOVE)
                .build();

        // 작업 추가
        if(availableBot != null){
            availableBot.updateStatus(BOT_BUSY);
            parkedVehicle.updateStatus(VEHICLE_MOVE);
            taskService.sendMessage(task);
        } else{
            taskService.addWaitingTask(task);
        }

        return task;
    }

    /**
     * 차량 위치 변경 handleMoveRequest을 사용 
     * moveVehicleRequestDTO -> MoveRequestDTO로 변환한 뒤 handleMoveRequest 사용
     * @param moveVehicleRequestDTO
     * @return
     */
    @Transactional
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
    @Transactional
    public ParkingBotDTO updateStatus(Integer serialNumber, Integer status) {
        ParkingBot parkingBot = parkingBotRepository.findBySerialNumber(serialNumber);
        if(parkingBot == null){
            throw new RestApiException(StatusCode.NOT_FOUND);
        }
        parkingBot.updateSerialNumberAndStatus(serialNumber,status);
        return ParkingBotConverter.entityToDto(parkingBot);
    }

    /**
     * 주차봇 전체 상태 변경
     * @param status
     * @return
     */
    @Transactional
    public List<ParkingBotDTO> updateAllStatus(Integer status) {

        List<ParkingBot> parkingBotList = parkingBotRepository.findAll();
        parkingBotList
                .forEach(parkingBot -> parkingBot.updateStatus(status));
        return ParkingBotConverter.entityToDtoList(parkingBotList);
    }

    /**
     * 주차봇 생성
     * @param parkingBotDTO
     * @return
     */
    @Transactional
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
    @Transactional
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

    @Transactional
    public boolean completeTask(Task task) {
        // 주차봇 상태 변경
        ParkingBot parkingBot = parkingBotRepository.findBySerialNumber(task.getParkingBotSerialNumber());
        if(parkingBot == null){
            throw new RestApiException(StatusCode.NOT_FOUND);
        }

        ParkedVehicle parkedVehicle = parkedVehicleRepository.findById(task.getParkedVehicleId()).orElse(null);
        if(parkedVehicle == null){
            throw new RestApiException(StatusCode.NOT_FOUND);
        }

        ParkingLotSpot parkingLotSpotStart = parkingLotSpotRepository.findBySpotNumber(task.getStart());
        ParkingLotSpot parkingLotSpotEnd = parkingLotSpotRepository.findBySpotNumber(task.getEnd());

        parkingBot.updateStatus(BOT_IDLE);

        if(task.getType() == ENTRANCE){
            if(parkingLotSpotEnd == null){
                throw new RestApiException(StatusCode.NO_SUCH_ELEMENT);
            }
            parkingLotSpotEnd.updateVehicleAndStatus(parkedVehicle,LOT_OCCUPIED);
            parkedVehicle.updateStatus(VEHICLE_PARKED);
        } else if(task.getType() == EXIT){
            if(parkingLotSpotStart == null){
                throw new RestApiException(StatusCode.NO_SUCH_ELEMENT);
            }
            parkingLotSpotStart.updateVehicleAndStatus(null,LOT_EMPTY);
            parkedVehicleRepository.delete(parkedVehicle);
        } else{
            if(parkingLotSpotStart == null || parkingLotSpotEnd == null){
                throw new RestApiException(StatusCode.NO_SUCH_ELEMENT);
            }
            parkingLotSpotStart.updateVehicleAndStatus(null,LOT_EMPTY);
            parkingLotSpotEnd.updateVehicleAndStatus(parkedVehicle,LOT_OCCUPIED);
            parkedVehicle.updateStatus(VEHICLE_PARKED);
        }

        if(taskService.hasWaitingTasks()){
            Task waitingTask = taskService.getNextWaitingTask();
            if(waitingTask != null){
                waitingTask = Task.builder()
                        .parkingBotSerialNumber(parkingBot.getSerialNumber())
                        .start(waitingTask.getStart())
                        .end(waitingTask.getEnd())
                        .type(waitingTask.getType())
                        .build();
                taskService.sendMessage(waitingTask);
            }
        }

        return true;
    }
}
