package com.mvp.parkinglot.controller;

import com.mvp.parkinglot.dto.ParkingLotMapDTO;
import com.mvp.parkinglot.dto.ParkingLotSettingDTO;
import com.mvp.parkinglot.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("/parking-lots")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @GetMapping("/map")
    public ParkingLotMapDTO getMap(){
        // 공통 예외처리 필요
        return parkingLotService.getMap();
    }

    @GetMapping("/setting")
    public ParkingLotSettingDTO getSetting(){
        // 공통 예외처리 필요
        return parkingLotService.getSetting();
    }

    @PutMapping("/setting")
    public ResponseEntity<?> updateSetting(@RequestBody ParkingLotSettingDTO parkingLotSettingDTO){
        // 공통 예외처리 필요
        parkingLotService.updateSetting(parkingLotSettingDTO);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


}
