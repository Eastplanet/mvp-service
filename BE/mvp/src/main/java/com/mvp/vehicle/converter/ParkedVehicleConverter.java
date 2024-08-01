package com.mvp.vehicle.converter;

import com.mvp.vehicle.dto.ParkedVehicleDTO;
import com.mvp.vehicle.entity.ParkedVehicle;

import java.util.List;
import java.util.stream.Collectors;

public class ParkedVehicleConverter {

    public static ParkedVehicleDTO entityToDto(ParkedVehicle parkedVehicle) {
        if (parkedVehicle == null) {
            return null;
        }
        else{
            return ParkedVehicleDTO.builder()
                    .entranceTime(parkedVehicle.getEntranceTime())
                    .image(parkedVehicle.getImage())
                    .licensePlate(parkedVehicle.getLicensePlate())
                    .build();
        }
    }

    public static ParkedVehicle dtoToEntity(ParkedVehicleDTO parkedVehicleDTO) {
        return ParkedVehicle.builder()
                .entranceTime(parkedVehicleDTO.getEntranceTime())
                .image(parkedVehicleDTO.getImage())
                .licensePlate(parkedVehicleDTO.getLicensePlate())
                .build();
    }

    public static List<ParkedVehicleDTO> entityListToDtoList(List<ParkedVehicle> parkedVehicleList) {
        return parkedVehicleList.stream()
                .map(ParkedVehicleConverter::entityToDto)
                .collect(Collectors.toList());
    }
}
