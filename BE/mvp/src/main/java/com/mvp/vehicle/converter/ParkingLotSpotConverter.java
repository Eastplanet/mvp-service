package com.mvp.vehicle.converter;

import com.mvp.vehicle.dto.ParkedVehicleDTO;
import com.mvp.vehicle.dto.ParkingLotSpotDTO;
import com.mvp.vehicle.entity.ParkingLotSpot;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSpotConverter {

    public static ParkingLotSpotDTO entityToDto(ParkingLotSpot parkingLotSpot) {
        return ParkingLotSpotDTO.builder()
                .status(parkingLotSpot.getStatus())
                .spotNumber(parkingLotSpot.getSpotNumber())
                .parkedVehicle(ParkedVehicleConverter.entityToDto(parkingLotSpot.getParkedVehicle()))
                .build();
    }

    public static ParkingLotSpot dtoToEntity(ParkingLotSpotDTO parkingLotSpotDTO) {
        return ParkingLotSpot.builder()
                .status(parkingLotSpotDTO.getStatus())
                .spotNumber(parkingLotSpotDTO.getSpotNumber())
                .parkedVehicle(ParkedVehicleConverter.dtoToEntity(parkingLotSpotDTO.getParkedVehicle()))
                .build();
    }

    public static List<ParkingLotSpotDTO> entityListToDtoList(List<ParkingLotSpot> parkingLotSpotList) {
        List<ParkingLotSpotDTO> list = new ArrayList<>();
        for (ParkingLotSpot parkingLotSpot : parkingLotSpotList) {
            ParkingLotSpotDTO parkingLotSpotDTO = entityToDto(parkingLotSpot);
            parkingLotSpotDTO.setParkedVehicle(ParkedVehicleConverter.entityToDto(parkingLotSpot.getParkedVehicle()));
            list.add(parkingLotSpotDTO);
        }
        return list;
    }
}
