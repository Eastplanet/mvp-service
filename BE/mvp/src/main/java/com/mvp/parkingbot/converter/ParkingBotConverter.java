package com.mvp.parkingbot.converter;

import com.mvp.parkingbot.dto.ParkingBotDTO;
import com.mvp.parkingbot.entity.ParkingBot;

public class ParkingBotConverter {
    public ParkingBotDTO entityToDto(ParkingBot parkingBot) {
        return ParkingBotDTO.builder()
                .serialNumber(parkingBot.getSerialNumber())
                .status(parkingBot.getStatus())
                .build();
    }

    public ParkingBot dtoToEntity(ParkingBotDTO parkingBotDTO) {
        return ParkingBot.builder()
                .serialNumber(parkingBotDTO.getSerialNumber())
                .status(parkingBotDTO.getStatus())
                .build();
    }
}
