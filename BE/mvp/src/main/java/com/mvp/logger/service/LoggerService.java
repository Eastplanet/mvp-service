package com.mvp.logger.service;

import com.mvp.common.exception.RestApiException;
import com.mvp.common.exception.StatusCode;
import com.mvp.logger.converter.VehicleLogConverter;
import com.mvp.logger.dto.EntranceLogDTO;
import com.mvp.logger.dto.ExitLogDTO;
import com.mvp.logger.dto.VehicleLogDTO;
import com.mvp.logger.entity.VehicleLog;
import com.mvp.logger.repository.VehicleLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoggerService {

    // @TODO : Enum으로 바꾸기
    private final int ENTER_TYPE = 0;
    private final int EXIT_TYPE = 1;

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

    public void createEntryLog(EntranceLogDTO entranceLogDTO) {

        if(entranceLogDTO == null || entranceLogDTO.getLicensePlate() == null || entranceLogDTO.getImage() == null || entranceLogDTO.getEntranceTime()== null){
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }

        VehicleLogDTO vehicleLogDTO = VehicleLogConverter.entranceDtoToVehicleLogDTO(entranceLogDTO);
        vehicleLogDTO.setType(ENTER_TYPE);
        VehicleLog vehicleLog = VehicleLogConverter.dtoToEntity(vehicleLogDTO);
        vehicleLogRepository.save(vehicleLog);
    }

    public void createExitLog(ExitLogDTO exitLogDTO){
        if(exitLogDTO == null || exitLogDTO.getLicensePlate() == null || exitLogDTO.getImage() == null || exitLogDTO.getFee() == null){
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }

        VehicleLogDTO vehicleLogDTO = VehicleLogConverter.exitDtoToVehicleLogDTO(exitLogDTO);
        vehicleLogDTO.setType(EXIT_TYPE);
        vehicleLogDTO.setExitTime(LocalDateTime.now());

        VehicleLog vehicleLog = VehicleLogConverter.dtoToEntity(vehicleLogDTO);
        vehicleLogRepository.save(vehicleLog);
    }


}
