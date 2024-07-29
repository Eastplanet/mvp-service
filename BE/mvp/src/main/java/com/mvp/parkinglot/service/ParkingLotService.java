package com.mvp.parkinglot.service;

import com.mvp.parkinglot.converter.ParkingLotMapConverter;
import com.mvp.parkinglot.converter.ParkingLotSettingConverter;
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


    public ParkingLotMapDTO getMap(){
        ParkingLotMap parkingLotMap = parkingLotMapRepository.findAll().get(0);
        return ParkingLotMapConverter.entityToDto(parkingLotMap);
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
