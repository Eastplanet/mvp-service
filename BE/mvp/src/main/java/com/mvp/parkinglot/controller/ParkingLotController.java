package com.mvp.parkinglot.controller;

import com.mvp.common.ResponseDto;
import com.mvp.common.exception.RestApiException;
import com.mvp.common.exception.StatusCode;
import com.mvp.parkinglot.dto.ParkingLotDTO;
import com.mvp.parkinglot.dto.ParkingLotMapDTO;
import com.mvp.parkinglot.dto.ParkingLotSettingDTO;
import com.mvp.parkinglot.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking-lots")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @GetMapping()
    public ResponseEntity<ResponseDto> getParkingLot() {
        // 공통 예외 처리 필요
        ParkingLotDTO result = parkingLotService.getParkingLot();
        if(result == null){
            throw new RestApiException(StatusCode.NO_SUCH_ELEMENT);
        }
        else {
            return ResponseDto.response(StatusCode.SUCCESS, result);
        }
    }

    @PostMapping()
    public ResponseEntity<ResponseDto> createParkingLot(@RequestBody ParkingLotDTO parkingLotDTO) {
        // 공통 예외 처리 필요
        ParkingLotDTO result = parkingLotService.createParkingLot(parkingLotDTO);
        if(result == null){
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }
        else{
            return ResponseDto.response(StatusCode.SUCCESS,result);
        }
    }

    @PutMapping()
    public ResponseEntity<ResponseDto> updateParkingLot(@RequestBody ParkingLotDTO parkingLotDTO) {
        ParkingLotDTO result = parkingLotService.updateParkingLot(parkingLotDTO);
        if(result == null){
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }
        else{
            return ResponseDto.response(StatusCode.SUCCESS,result);
        }
    }

    @GetMapping("/map")
    public ResponseEntity<ResponseDto> getMap(){
        // 공통 예외처리 필요
        ParkingLotMapDTO result = parkingLotService.getMap();
        if(result == null){
            throw new RestApiException(StatusCode.NOT_FOUND);
        }
        else{
            return ResponseDto.response(StatusCode.SUCCESS,result);
        }
    }

    @PutMapping("/map")
    public ResponseEntity<ResponseDto> updateMap(@RequestBody ParkingLotMapDTO parkingLotMapDTO) {
        // 공통 예외처리 필요
        ParkingLotMapDTO result = parkingLotService.updateMap(parkingLotMapDTO);
        if(result == null){
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }
        else{
            return ResponseDto.response(StatusCode.SUCCESS,result);
        }

    }


    @GetMapping("/setting")
    public ResponseEntity<ResponseDto> getSetting(){
        // 공통 예외처리 필요
        ParkingLotSettingDTO result = parkingLotService.getSetting();
        if(result == null){
            throw new RestApiException(StatusCode.NOT_FOUND);
        }
        else {
            return ResponseDto.response(StatusCode.SUCCESS,result);
        }
    }

    @PutMapping("/setting")
    public ResponseEntity<ResponseDto> updateSetting(@RequestBody ParkingLotSettingDTO parkingLotSettingDTO){
        // 공통 예외처리 필요
        ParkingLotSettingDTO result = parkingLotService.updateSetting(parkingLotSettingDTO);
        if(result == null){
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }
        return ResponseDto.response(StatusCode.SUCCESS,result);
    }


}
