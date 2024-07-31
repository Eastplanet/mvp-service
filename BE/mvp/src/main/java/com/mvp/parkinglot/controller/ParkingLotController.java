package com.mvp.parkinglot.controller;

import com.mvp.parkinglot.dto.ParkingLotDTO;
import com.mvp.parkinglot.dto.ParkingLotMapDTO;
import com.mvp.parkinglot.dto.ParkingLotSettingDTO;
import com.mvp.parkinglot.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/parking-lots")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @GetMapping()
    public ParkingLotDTO getParkingLot() {
        // 공통 예외 처리 필요
        return parkingLotService.getParkingLot();
    }

    @PostMapping()
    public ResponseEntity<?> createParkingLot(@RequestBody ParkingLotDTO parkingLotDTO) {
        // 공통 예외 처리 필요
        ParkingLotDTO parkingLot = parkingLotService.createParkingLot(parkingLotDTO);
        if(parkingLot == null){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity<?> updateParkingLot(@RequestBody ParkingLotDTO parkingLotDTO) {
        ParkingLotDTO result = parkingLotService.updateParkingLot(parkingLotDTO);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/map")
    public ParkingLotMapDTO getMap(){
        // 공통 예외처리 필요
        return parkingLotService.getMap();
    }

    @PutMapping("/map")
    public ResponseEntity<?> updateMap(@RequestBody ParkingLotMapDTO parkingLotMapDTO) {
        // 공통 예외처리 필요
        ParkingLotMapDTO result = parkingLotService.updateMap(parkingLotMapDTO);
        if(result != null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

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
