package com.mvp.parkingbot.controller;

import com.mvp.parkingbot.dto.ParkingBotDTO;
import com.mvp.parkingbot.service.ParkingBotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking-bot")
public class ParkingBotController {
    private ParkingBotService parkingBotService;

    /**
     * 주차 봇 등록
     * @param parkingBotDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<String> parkingBot(@RequestBody ParkingBotDTO parkingBotDTO) {
        ParkingBotDTO savedParkingBotDTO = parkingBotService.registerParkingBot(parkingBotDTO);

        if(savedParkingBotDTO != null) {
            return ResponseEntity.ok(savedParkingBotDTO.toString());
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
