package com.mvp.logs.service;

import com.mvp.logs.converter.VehicleLogConverter;
import com.mvp.logs.dto.VehicleLogDTO;
import com.mvp.logs.entity.VehicleLog;
import com.mvp.logs.repository.VehicleLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogsService {
    private final VehicleLogRepository vehicleLogRepository;

    public List<VehicleLogDTO> findAllByEntranceTimeBetween(LocalDateTime start, LocalDateTime end) {
        List<VehicleLog> allByEntranceTimeBetween = vehicleLogRepository.findAllByEntranceTimeBetween(start, end);
        return VehicleLogConverter.entityListToDtoList(allByEntranceTimeBetween);
    }

    public List<VehicleLogDTO> findAllByExitTimeBetween(LocalDateTime start, LocalDateTime end) {
        List<VehicleLog> allByExitTimeBetween = vehicleLogRepository.findAllByExitTimeBetween(start, end);
        return VehicleLogConverter.entityListToDtoList(allByExitTimeBetween);
    }


    public List<VehicleLogDTO> findByEntranceTimeBetween(LocalDateTime start, LocalDateTime end, String licensePlate) {
        List<VehicleLog> byLicensePlate = vehicleLogRepository.findByLicensePlateEntranceTimeBetween(start, end, licensePlate);
        return VehicleLogConverter.entityListToDtoList(byLicensePlate);
    }
}
