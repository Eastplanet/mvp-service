package com.mvp.parkingbot.service;

import com.mvp.parkingbot.dto.EnterRequestDTO;
import com.mvp.parkingbot.dto.MoveRequestDTO;
import com.mvp.parkingbot.dto.Task;
import com.mvp.parkingbot.entity.ParkingBot;
import com.mvp.parkingbot.repository.ParkingBotRepository;
import com.mvp.utils.TaskQueue;
import com.mvp.vehicle.entity.ParkedVehicle;
import com.mvp.vehicle.entity.ParkingLotSpot;
import com.mvp.vehicle.repository.ParkedVehicleRepository;
import com.mvp.vehicle.repository.ParkingLotSpotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
// Parkingbotservice
public class ParkingBotService {
    private final ParkingLotSpotRepository parkingLotSpotRepository;
    private final ParkingBotRepository parkingBotRepository;
    private final ParkedVehicleRepository parkedVehicleRepository;
    private TaskQueue taskQueue;

    /**
     * 입차 처리
     * @param enterRequestDTO
     * @return
     */
    public Task handleEnterRequest(EnterRequestDTO enterRequestDTO) {
        Optional<ParkingLotSpot> availableSpot = parkingLotSpotRepository.findFirstByStatus(0);

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
                .status(1)
                .build();
        parkingLotSpotRepository.save(spot);

        Optional<ParkingBot> availableBot = parkingBotRepository.findFirstByStatus(0);
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

        return task;
    }

    public boolean handleExitRequest(String licensePlate) {
        ParkedVehicle parkedVehicle = parkedVehicleRepository.findByLicensePlate(licensePlate);
        if(parkedVehicle == null){
            return false;
        }

        ParkingLotSpot spot = parkingLotSpotRepository.findByParkedVehicleId(parkedVehicle.getId());
        if(spot != null){
            spot = ParkingLotSpot.builder()
                    .id(spot.getId())
                    .spotNumber(spot.getSpotNumber())
                    .parkedVehicle(null)
                    .status(0)
                    .build();
            parkingLotSpotRepository.save(spot);
        }

        parkedVehicleRepository.delete(parkedVehicle);

        Optional<ParkingBot> availableBot = parkingBotRepository.findFirstByStatus(0);

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

        return true;
    }

    public Task getTaskfromQueue() {
        return taskQueue.getNextTask();
    }

    public void updateParingBotStatus(Integer parkingBotSerialNumber, int i) {
        ParkingBot parkingBot = parkingBotRepository.findBySerialNumber(parkingBotSerialNumber);
        parkingBot = ParkingBot.builder()
                .serialNumber(parkingBot.getSerialNumber())
                .status(i)
                .build();
        parkingBotRepository.save(parkingBot);
    }

    public Task handleMoveRequest(MoveRequestDTO moveRequestDTO) {
        int start = moveRequestDTO.getStart();
        int end = moveRequestDTO.getEnd();

        ParkingLotSpot startSpot = parkingLotSpotRepository.findBySpotNumber(start);
        ParkingLotSpot endSpot = parkingLotSpotRepository.findBySpotNumber(end);

        if(startSpot == null || endSpot == null){
            return null;
        }
        if(startSpot.getParkedVehicle() == null){
            return null;
        }
        if(endSpot.getParkedVehicle() != null){
            return null;
        }

        ParkedVehicle vehicle = startSpot.getParkedVehicle();
        startSpot = ParkingLotSpot.builder()
                .id(startSpot.getId())
                .spotNumber(startSpot.getSpotNumber())
                .parkedVehicle(null)
                .status(0)
                .build();

        endSpot = ParkingLotSpot.builder()
                .id(endSpot.getId())
                .spotNumber(endSpot.getSpotNumber())
                .parkedVehicle(vehicle)
                .status(1)
                .build();

        parkingLotSpotRepository.save(startSpot);
        parkingLotSpotRepository.save(endSpot);

        ParkingBot availableBot = parkingBotRepository.findFirstByStatus(0).orElse(null);
        Task task = Task.builder()
                .parkingBotSerialNumber(availableBot != null ? availableBot.getSerialNumber() : null)
                .start(start)
                .end(end)
                .build();

        if(availableBot != null){
            taskQueue.addTask(task);
        } else{
            taskQueue.addWaitingTask(task);
        }

        return task;
    }
}
