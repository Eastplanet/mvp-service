package com.mvp.vehicle.service;

import com.mvp.vehicle.entity.ParkedVehicle;
import com.mvp.vehicle.repository.ParkedVehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VehicleService {
    private final ParkedVehicleRepository parkedVehicleRepository;

    public ParkedVehicle getParkedVehicle(Long vehicleId) {
        ParkedVehicle parkedVehicle = parkedVehicleRepository.findById(vehicleId).orElse(null);
        if(parkedVehicle != null){
            return parkedVehicle;
        } else {
            return null;
        }
    }

    public List<ParkedVehicle> getParkedVehicleList() {
        return parkedVehicleRepository.findAll();
    }
}
