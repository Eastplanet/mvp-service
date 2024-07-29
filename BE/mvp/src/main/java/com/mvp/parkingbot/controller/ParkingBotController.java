package com.mvp.parkingbot.controller;

import com.mvp.parkingbot.dto.EnterRequestDTO;
import com.mvp.parkingbot.dto.ParkingBotDTO;
import com.mvp.parkingbot.dto.Task;
import com.mvp.parkingbot.service.ParkingBotService;
import com.mvp.vehicle.dto.ParkedVehicleDTO;
import com.mvp.vehicle.repository.ParkedVehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking-bot")
@AllArgsConstructor
public class ParkingBotController {
    private ParkingBotService parkingBotService;

    @PostMapping("/enter")
    public ResponseEntity<Task> enterRequest(@RequestBody EnterRequestDTO enterRequestDTO) {
        Task task = parkingBotService.handleEnterRequest(enterRequestDTO);
        if(task != null){
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
