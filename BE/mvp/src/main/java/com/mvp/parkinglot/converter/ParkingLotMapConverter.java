package com.mvp.parkinglot.converter;

import com.mvp.parkinglot.dto.ParkingLotMapDTO;
import com.mvp.parkinglot.entity.ParkingLotMap;

public class ParkingLotMapConverter  {

    public ParkingLotMapDTO entityToDto(ParkingLotMap parkingLotMap) {
        return ParkingLotMapDTO.builder()
                .image(parkingLotMap.getImage())
                .mapInfo(parkingLotMap.getMapInfo())
                .build();
    }

    public ParkingLotMap dtoToEntity(ParkingLotMapDTO parkingLotMapDTO) {
        return ParkingLotMap.builder()
                .image(parkingLotMapDTO.getImage())
                .mapInfo(parkingLotMapDTO.getMapInfo())
                .build();
    }
}
