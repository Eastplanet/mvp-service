package com.mvp.parkinglot.converter;

import com.mvp.parkinglot.dto.ParkingLotDTO;
import com.mvp.parkinglot.entity.ParkingLot;

public class ParkingLotConverter {
    private final ParkingLotSettingConverter parkingLotSettingConverter = new ParkingLotSettingConverter();
    private final ParkingLotMapConverter parkingLotMapConverter = new ParkingLotMapConverter();

    public ParkingLotDTO entityToDto(ParkingLot parkingLot) {
        return ParkingLotDTO.builder()
                .name(parkingLot.getName())
                .address(parkingLot.getAddress())
                .parkingLotSetting(parkingLotSettingConverter.entityToDto(parkingLot.getParkingLotSetting()))
                .parkingLotMap(parkingLotMapConverter.entityToDto(parkingLot.getParkingLotMap()))
                .build();
    }

    public ParkingLot dtoToEntity(ParkingLotDTO parkingLotDTO) {
        return ParkingLot.builder()
                .name(parkingLotDTO.getName())
                .address(parkingLotDTO.getAddress())
                .parkingLotSetting(parkingLotSettingConverter.dtoToEntity(parkingLotDTO.getParkingLotSetting()))
                .parkingLotMap(parkingLotMapConverter.dtoToEntity(parkingLotDTO.getParkingLotMap()))
                .build();
    }

}
