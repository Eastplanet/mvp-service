package com.mvp.parkinglot.controller;

import com.mvp.parkinglot.dto.ParkingLotMapDTO;
import com.mvp.parkinglot.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("/parking-lots")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @GetMapping("/map")
    public ParkingLotMapDTO getMap(){
        return parkingLotService.getMap();
    }
}
