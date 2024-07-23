package com.mvp.parkingbot.controller;

import com.mvp.parkingbot.dto.ParkingBotDTO;
import com.mvp.parkingbot.service.ParkingBotService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking-bot")
@AllArgsConstructor
public class ParkingBotController {
    private ParkingBotService parkingBotService;

    /**
     * 주차 봇 등록
     * @param parkingBotDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<ParkingBotDTO> parkingBot(@RequestBody ParkingBotDTO parkingBotDTO) {
        ParkingBotDTO savedParkingBotDTO = parkingBotService.registerParkingBot(parkingBotDTO);

        if(savedParkingBotDTO != null) {
            return ResponseEntity.ok(savedParkingBotDTO);
        } else{
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 주차 봇 삭제
     * @param parkingBotId
     * @return
     */
    @DeleteMapping("/{parkingBotId}")
    public ResponseEntity<Void> deleteParkingBot(@PathVariable Long parkingBotId) {
        if(parkingBotService.deleteParkingBot(parkingBotId)){
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.badRequest().build();
        }
    }
}
