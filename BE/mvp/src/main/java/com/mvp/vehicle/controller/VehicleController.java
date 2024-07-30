package com.mvp.vehicle.controller;

import com.mvp.vehicle.dto.ParkedVehicleDTO;
import com.mvp.vehicle.entity.ParkedVehicle;
import com.mvp.vehicle.service.VehicleService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parked-vehicle")
@AllArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    /**
     * 주차된 차량 정보 조회
     * @param vehicleId
     * @return
     */
    @GetMapping("/{vehicleId}")
    public ResponseEntity<ParkedVehicleDTO> getParkedVehicle(@PathVariable Long vehicleId){
        ParkedVehicleDTO parkedVehicleDTO = vehicleService.getParkedVehicle(vehicleId);
        if(parkedVehicleDTO != null){
            return ResponseEntity.ok(parkedVehicleDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 주차된 차량 목록 조회
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<ParkedVehicleDTO>> getParkedVehicleList(){
        List<ParkedVehicleDTO> parkedVehicleList = vehicleService.getParkedVehicleList();

        if(!parkedVehicleList.isEmpty()){
            return ResponseEntity.ok(parkedVehicleList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 주차된 차량 번호로 조회
     * @param backNum
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ParkedVehicleDTO>> getParkedVehicleByBackNum(@RequestParam String backNum){
        List<ParkedVehicleDTO> parkedVehicleList = vehicleService.getParkedVehicleListByBackNum(backNum);

        if(!parkedVehicleList.isEmpty()){
            return ResponseEntity.ok(parkedVehicleList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
