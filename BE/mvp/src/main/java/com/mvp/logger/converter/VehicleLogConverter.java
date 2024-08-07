package com.mvp.logger.converter;

import com.mvp.logger.dto.EntranceLogDTO;
import com.mvp.logger.dto.ExitLogDTO;
import com.mvp.logger.dto.VehicleLogDTO;
import com.mvp.logger.entity.VehicleLog;

import java.util.ArrayList;
import java.util.List;

public class VehicleLogConverter {

    public static VehicleLogDTO entityToDto(VehicleLog vehicleLog) {
        return VehicleLogDTO.builder()
                .licensePlate(vehicleLog.getLicensePlate())
                .entranceTime(vehicleLog.getEntranceTime())
                .exitTime(vehicleLog.getExitTime())
                .fee(vehicleLog.getFee())
                .type(vehicleLog.getType())
                .image(vehicleLog.getImage())
                .build();
    }

    public static VehicleLog dtoToEntity(VehicleLogDTO vehicleLogDTO) {
        return VehicleLog.builder()
                .licensePlate(vehicleLogDTO.getLicensePlate())
                .entranceTime(vehicleLogDTO.getEntranceTime())
                .type(vehicleLogDTO.getType())
                .exitTime(vehicleLogDTO.getExitTime())
                .fee(vehicleLogDTO.getFee())
                .build();
    }

    public static List<VehicleLogDTO> entityListToDtoList(List<VehicleLog> vehicleLogList) {
        List<VehicleLogDTO> dtoList = new ArrayList<>();
        for (VehicleLog vehicleLog : vehicleLogList) {
            dtoList.add(entityToDto(vehicleLog));
        }
        return dtoList;
    }

    public static VehicleLogDTO entranceDtoToVehicleLogDTO(EntranceLogDTO entranceLogDTO) {
        return VehicleLogDTO.builder()
                .licensePlate(entranceLogDTO.getLicensePlate())
                .image(entranceLogDTO.getImage())
                .entranceTime(entranceLogDTO.getEntranceTime())
                .build();
    }

    public static VehicleLogDTO exitDtoToVehicleLogDTO(ExitLogDTO exitLogDTO) {
        return VehicleLogDTO.builder()
                .licensePlate(exitLogDTO.getLicensePlate())
                .fee(exitLogDTO.getFee())
                .image(exitLogDTO.getImage())
                .entranceTime(exitLogDTO.getEntranceTime())
                .build();
    }
}
