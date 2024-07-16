package com.mvp.parking.controller;

import com.mvp.parking.dto.EnterDto;
import com.mvp.parking.service.ParkingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking")
@AllArgsConstructor
public class ParkingController {

    private final ParkingService parkingService;

    @PostMapping("/enter")
    public String enter(@RequestBody EnterDto enterDto){
        System.out.println("enter");
        parkingService.enter(enterDto);
        return "OK";
    }
}
