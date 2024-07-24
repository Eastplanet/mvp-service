package com.mvp.vehicle.converter;

import com.mvp.vehicle.dto.ParkingLotSpotDTO;
import com.mvp.vehicle.entity.ParkingLotSpot;

public class ParkingLotSpotConverter {
    private final ParkedVehicleConverter parkedVehicleConverter = new ParkedVehicleConverter();

    public ParkingLotSpotDTO entityToDto(ParkingLotSpot parkingLotSpot) {
        return ParkingLotSpotDTO.builder()
                .status(parkingLotSpot.getStatus())
                .spotNumber(parkingLotSpot.getSpotNumber())
                .parkedVehicle(parkedVehicleConverter.entityToDto(parkingLotSpot.getParkedVehicle()))
                .build();
    }

    public ParkingLotSpot dtoToEntity(ParkingLotSpotDTO parkingLotSpotDTO) {
        return ParkingLotSpot.builder()
                .status(parkingLotSpotDTO.getStatus())
                .spotNumber(parkingLotSpotDTO.getSpotNumber())
                .parkedVehicle(parkedVehicleConverter.dtoToEntity(parkingLotSpotDTO.getParkedVehicle()))
                .build();
    }
}
