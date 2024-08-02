package com.mvp.stats.controller;

import com.mvp.common.ResponseDto;
import com.mvp.common.exception.StatusCode;
import com.mvp.stats.dto.HomePageInitDto;
import com.mvp.stats.dto.ParkingLogReq;
import com.mvp.stats.dto.ParkingLogRes;
import com.mvp.stats.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/home-init")
    public ResponseEntity<ResponseDto> initHomePage(){
        HomePageInitDto initHomePage = statsService.getInitHomePage();
        return ResponseDto.response(StatusCode.SUCCESS, initHomePage);
    }

    @GetMapping("/parking-log")
    public ResponseEntity<ResponseDto> parkingLog(@RequestBody ParkingLogReq parkingLogReq){
        List<ParkingLogRes> parkingLot = statsService.getParkingLot(parkingLogReq);
        return ResponseDto.response(StatusCode.SUCCESS, parkingLot);
    }
}
