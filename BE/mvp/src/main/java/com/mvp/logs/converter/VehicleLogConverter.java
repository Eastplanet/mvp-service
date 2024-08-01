package com.mvp.logs.converter;

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
}
