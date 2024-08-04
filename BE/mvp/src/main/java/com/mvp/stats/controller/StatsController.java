package com.mvp.stats.controller;

import com.mvp.common.ResponseDto;
import com.mvp.common.exception.StatusCode;
import com.mvp.stats.dto.HomePageInitDto;
import com.mvp.stats.dto.ParkingLogReq;
import com.mvp.stats.dto.ParkingLogRes;
import com.mvp.stats.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/home-init")
    public ResponseEntity<ResponseDto> initHomePage(){
        HomePageInitDto initHomePage = statsService.getInitHomePage();
        return ResponseDto.response(StatusCode.SUCCESS, initHomePage);
    }

    @GetMapping("/parking-log")
    public ResponseEntity<ResponseDto> parkingLog(@ModelAttribute ParkingLogReq parkingLogReq){
        System.out.println("parkingLogReq = " + parkingLogReq);
        List<ParkingLogRes> parkingLot = statsService.getParkingLot(parkingLogReq);
        return ResponseDto.response(StatusCode.SUCCESS, parkingLot);
    }
}
