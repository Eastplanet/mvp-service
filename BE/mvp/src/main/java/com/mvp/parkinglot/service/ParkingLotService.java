package com.mvp.parkinglot.service;

import com.mvp.parkinglot.converter.ParkingLotConverter;
import com.mvp.parkinglot.converter.ParkingLotMapConverter;
import com.mvp.parkinglot.converter.ParkingLotSettingConverter;
import com.mvp.parkinglot.dto.ParkingLotDTO;
import com.mvp.parkinglot.dto.ParkingLotMapDTO;
import com.mvp.parkinglot.dto.ParkingLotSettingDTO;
import com.mvp.parkinglot.entity.ParkingLot;
import com.mvp.parkinglot.entity.ParkingLotMap;
import com.mvp.parkinglot.entity.ParkingLotSetting;
import com.mvp.parkinglot.repository.ParkingLotMapRepository;
import com.mvp.parkinglot.repository.ParkingLotRepository;
import com.mvp.parkinglot.repository.ParkingLotSettingRepository;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotMapRepository parkingLotMapRepository;
    private final ParkingLotSettingRepository parkingLotSettingRepository;

    public ParkingLotDTO getParkingLot(){
        ParkingLot parkingLot = parkingLotRepository.findAll().get(0);
        return ParkingLotConverter.entityToDto(parkingLot);
    }

    public ParkingLotDTO createParkingLot(ParkingLotDTO parkingLotDTO){
        ParkingLot parkingLot = ParkingLotConverter.dtoDoEntity(parkingLotDTO);
        ParkingLot save = parkingLotRepository.save(parkingLot);
        return ParkingLotConverter.entityToDto(save);
    }

    public ParkingLotDTO updateParkingLot(ParkingLotDTO parkingLotDTO){
        ParkingLot parkingLot = ParkingLotConverter.dtoDoEntity(parkingLotDTO);
        ParkingLot save = parkingLotRepository.save(parkingLot);
        return ParkingLotConverter.entityToDto(save);
    }

    public ParkingLotMapDTO getMap(){
        ParkingLotMap parkingLotMap = parkingLotMapRepository.findAll().get(0);
        return ParkingLotMapConverter.entityToDto(parkingLotMap);
    }

    public ParkingLotMapDTO updateMap(ParkingLotMapDTO parkingLotMapDTO){
        ParkingLotMap parkingLotMap = ParkingLotMapConverter.dtoToEntity(parkingLotMapDTO);
        ParkingLotMap save = parkingLotMapRepository.save(parkingLotMap);
        return ParkingLotMapConverter.entityToDto(save);
    }

    public ParkingLotSettingDTO getSetting(){
        ParkingLotSetting setting = parkingLotSettingRepository.findAll().get(0);
        return ParkingLotSettingConverter.entityToDto(setting);
    }

    @Transactional
    public void updateSetting(ParkingLotSettingDTO parkingLotSettingDTO) {
        ParkingLotSetting parkingLotSetting = parkingLotSettingRepository.findAll().get(0);
        parkingLotSetting.update(parkingLotSettingDTO);
    }
}
