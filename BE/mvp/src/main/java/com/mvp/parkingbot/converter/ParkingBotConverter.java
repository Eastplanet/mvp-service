package com.mvp.parkingbot.converter;

import com.mvp.parkingbot.dto.ParkingBotDTO;
import com.mvp.parkingbot.entity.ParkingBot;

import java.util.List;

public class ParkingBotConverter {
    public static ParkingBotDTO entityToDto(ParkingBot parkingBot) {
        return ParkingBotDTO.builder()
                .serialNumber(parkingBot.getSerialNumber())
                .status(parkingBot.getStatus())
                .build();
    }

    public static ParkingBot dtoToEntity(ParkingBotDTO parkingBotDTO) {
        return ParkingBot.builder()
                .serialNumber(parkingBotDTO.getSerialNumber())
                .status(parkingBotDTO.getStatus())
                .build();
    }

    public static List<ParkingBotDTO> entityToDtoList(List<ParkingBot> parkingBotList) {
        return parkingBotList.stream()
                .map(ParkingBotConverter::entityToDto)
                .toList();
    }
}
