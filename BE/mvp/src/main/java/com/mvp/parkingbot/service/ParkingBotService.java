package com.mvp.parkingbot.service;

import com.mvp.parkingbot.converter.ParkingBotConverter;
import com.mvp.parkingbot.dto.EnterRequestDTO;
import com.mvp.parkingbot.dto.ParkingBotDTO;
import com.mvp.parkingbot.dto.Task;
import com.mvp.parkingbot.entity.ParkingBot;
import com.mvp.parkingbot.repository.ParkingBotRepository;
import com.mvp.parkinglot.entity.ParkingLot;
import com.mvp.parkinglot.repository.ParkingLotRepository;
import com.mvp.parkinglot.service.ParkingLotService;
import com.mvp.vehicle.dto.ParkedVehicleDTO;
import com.mvp.vehicle.entity.ParkedVehicle;
import com.mvp.vehicle.entity.ParkingLotSpot;
import com.mvp.vehicle.repository.ParkedVehicleRepository;
import com.mvp.vehicle.repository.ParkingLotSpotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@AllArgsConstructor
// Parkingbotservice
public class ParkingBotService {
    private final ParkingLotSpotRepository parkingLotSpotRepository;
    private final ParkingBotRepository parkingBotRepository;
    private final ParkedVehicleRepository parkedVehicleRepository;
    private static final Queue<Task> taskQueue = new ConcurrentLinkedQueue<>();
    private static final Queue<Task> waitingQueue = new ConcurrentLinkedQueue<>();

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
            waitingQueue.add(task);
        } else{
            task = Task.builder()
                    .parkingBotSerialNumber(availableBot.get().getSerialNumber())
                    .start(0)
                    .end(spot.getSpotNumber())
                    .build();
            taskQueue.add(task);
        }

        return task;
    }

    public Task getNextTask() {
        return taskQueue.poll();
    }

    public Task getWaitingTask() {
        return waitingQueue.poll();
    }

    public boolean hasTask() {
        return !taskQueue.isEmpty();
    }

    public boolean hasWaitingTask() {
        return !waitingQueue.isEmpty();
    }
}
