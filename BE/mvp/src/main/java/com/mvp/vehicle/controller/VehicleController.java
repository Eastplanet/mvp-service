package com.mvp.vehicle.controller;

import com.mvp.common.ResponseDto;
import com.mvp.common.exception.RestApiException;
import com.mvp.common.exception.StatusCode;
import com.mvp.vehicle.dto.DiscountDTO;
import com.mvp.vehicle.dto.ParkedVehicleDTO;
import com.mvp.vehicle.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parked-vehicle")
@AllArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    /**
     * 주차된 차량 정보 조회
     * @param vehicleId
     * @return
     */
    @GetMapping("/{vehicleId}")
    public ResponseEntity<ResponseDto> getParkedVehicle(@PathVariable Long vehicleId){
        ParkedVehicleDTO parkedVehicleDTO = vehicleService.getParkedVehicle(vehicleId);
        if(parkedVehicleDTO != null){
            return ResponseDto.response(StatusCode.SUCCESS, parkedVehicleDTO);
        } else {
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 주차된 차량 목록 조회
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<ResponseDto> getParkedVehicleList(){
        List<ParkedVehicleDTO> parkedVehicleList = vehicleService.getParkedVehicleList();

        if(!parkedVehicleList.isEmpty()){
            return ResponseDto.response(StatusCode.SUCCESS, parkedVehicleList);
        } else {
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 주차된 차량 번호로 조회
     * @param backNum
     * @return
     */
    @GetMapping
    public ResponseEntity<ResponseDto> getParkedVehicleByBackNum(@RequestParam String backNum){
        List<ParkedVehicleDTO> parkedVehicleList = vehicleService.getParkedVehicleListByBackNum(backNum);

        if(!parkedVehicleList.isEmpty()){
            return ResponseDto.response(StatusCode.SUCCESS, parkedVehicleList);
        } else {
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/discount")
    public ResponseEntity<ResponseDto> giveDiscount(@RequestBody DiscountDTO discountDTO){
        ParkedVehicleDTO parkedVehicleDTO = vehicleService.giveDiscount(discountDTO);

        if(parkedVehicleDTO != null){
            return ResponseDto.response(StatusCode.SUCCESS, parkedVehicleDTO);
        } else {
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }
}
