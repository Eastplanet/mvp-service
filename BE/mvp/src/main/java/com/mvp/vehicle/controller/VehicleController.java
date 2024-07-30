package com.mvp.vehicle.controller;

import com.mvp.vehicle.entity.ParkedVehicle;
import com.mvp.vehicle.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parked-vehicle")
@AllArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping("/{vehicleId}")
    public ResponseEntity<ParkedVehicle> getParkedVehicle(@PathVariable Long vehicleId){
        ParkedVehicle parkedVehicle = vehicleService.getParkedVehicle(vehicleId);
        if(parkedVehicle != null){
            return ResponseEntity.ok(parkedVehicle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<ParkedVehicle>> getParkedVehicleList(){
        List<ParkedVehicle> parkedVehicleList = vehicleService.getParkedVehicleList();

        if(!parkedVehicleList.isEmpty()){
            return ResponseEntity.ok(parkedVehicleList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
