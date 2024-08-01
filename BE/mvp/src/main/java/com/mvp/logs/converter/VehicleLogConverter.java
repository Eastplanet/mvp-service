package com.mvp.logs.converter;

import com.mvp.logs.dto.VehicleLogDTO;
import com.mvp.logs.entity.VehicleLog;

public class VehicleLogConverter {

    public VehicleLogDTO entityToDto(VehicleLog vehicleLog) {
        return VehicleLogDTO.builder()
                .licensePlate(vehicleLog.getLicensePlate())
                .entranceTime(vehicleLog.getEntranceTime())
                .exitTime(vehicleLog.getExitTime())
                .fee(vehicleLog.getFee())
                .build();
    }

    public VehicleLog dtoToEntity(VehicleLogDTO vehicleLogDTO) {
        return VehicleLog.builder()
                .licensePlate(vehicleLogDTO.getLicensePlate())
                .entranceTime(vehicleLogDTO.getEntranceTime())
                .exitTime(vehicleLogDTO.getExitTime())
                .fee(vehicleLogDTO.getFee())
                .build();
    }
}
