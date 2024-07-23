package com.mvp.parkingbot.converter;

import com.mvp.parkingbot.dto.ParkingBotDTO;
import com.mvp.parkingbot.entity.ParkingBot;
import com.mvp.parkinglot.entity.ParkingLot;

public class ParkingBotConverter {
    public static ParkingBot toEntity(ParkingBotDTO dto, ParkingLot parkingLot){
        return ParkingBot.builder()
                .id(dto.getId())
                .serialNumber(dto.getSerialNumber())
                .status(dto.getStatus())
                .parkingLot(parkingLot)
                .build();
    }

    public static ParkingBotDTO toDto(ParkingBot entity){
        return ParkingBotDTO.builder()
                .id(entity.getId())
                .serialNumber(entity.getSerialNumber())
                .status(entity.getStatus())
                .parkingLotId(entity.getParkingLot().getId())
                .build();
    }
}
