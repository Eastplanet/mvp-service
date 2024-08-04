package com.mvp.logs.converter;

import com.mvp.logs.dto.EntranceLogDTO;
import com.mvp.logs.dto.ExitLogDTO;
import com.mvp.logs.dto.VehicleLogDTO;
import com.mvp.logs.entity.VehicleLog;

import java.util.ArrayList;
import java.util.List;

public class VehicleLogConverter {

    public static VehicleLogDTO entityToDto(VehicleLog vehicleLog) {
        return VehicleLogDTO.builder()
                .licensePlate(vehicleLog.getLicensePlate())
                .entranceTime(vehicleLog.getEntranceTime())
                .exitTime(vehicleLog.getExitTime())
                .fee(vehicleLog.getFee())
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
            VehicleLogDTO dto = VehicleLogDTO.builder()
                    .licensePlate(vehicleLog.getLicensePlate())
                    .entranceTime(vehicleLog.getEntranceTime())
                    .exitTime(vehicleLog.getExitTime())
                    .fee(vehicleLog.getFee())
                    .build();
            dtoList.add(dto);
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
                .build();
    }
}
