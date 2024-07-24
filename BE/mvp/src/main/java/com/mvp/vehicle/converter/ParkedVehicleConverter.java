package com.mvp.vehicle.converter;

import com.mvp.vehicle.dto.ParkedVehicleDTO;
import com.mvp.vehicle.entity.ParkedVehicle;

public class ParkedVehicleConverter {

    public ParkedVehicleDTO entityToDto(ParkedVehicle parkedVehicle) {
        return ParkedVehicleDTO.builder()
                .entranceTime(parkedVehicle.getEntranceTime())
                .image(parkedVehicle.getImage())
                .licensePlate(parkedVehicle.getLicensePlate())
                .build();
    }

    public ParkedVehicle dtoToEntity(ParkedVehicleDTO parkedVehicleDTO) {
        return ParkedVehicle.builder()
                .entranceTime(parkedVehicleDTO.getEntranceTime())
                .image(parkedVehicleDTO.getImage())
                .licensePlate(parkedVehicleDTO.getLicensePlate())
                .build();
    }
}
