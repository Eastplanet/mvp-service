package com.mvp.parkinglot.converter;

import com.mvp.parkinglot.dto.ParkingLotDTO;
import com.mvp.parkinglot.dto.ParkingLotMapDTO;
import com.mvp.parkinglot.dto.ParkingLotSettingDTO;
import com.mvp.parkinglot.entity.ParkingLot;
import com.mvp.parkinglot.entity.ParkingLotMap;
import com.mvp.parkinglot.entity.ParkingLotSetting;

/**
 * ParkingLot의 Entity <-> DTO 변환을 위한 클래스입니다.
 * 연관관계 Entity도 한번에 로딩합니다.
 */
public class ParkingLotConverter {

    public static ParkingLotDTO entityToDto(ParkingLot parkingLot) {
        return ParkingLotDTO.builder()
                .name(parkingLot.getName())
                .address(parkingLot.getAddress())
                .parkingLotSetting(ParkingLotSettingConverter.entityToDto(parkingLot.getParkingLotSetting()))
                .parkingLotMap(ParkingLotMapConverter.entityToDto(parkingLot.getParkingLotMap()))
                .build();
    }

    public static ParkingLot dtoDoEntity(ParkingLotDTO parkingLotDTO) {
        return ParkingLot.builder()
                .name(parkingLotDTO.getName())
                .address(parkingLotDTO.getAddress())
                .parkingLotSetting(ParkingLotSettingConverter.dtoToEntity(parkingLotDTO.getParkingLotSetting()))
                .parkingLotMap(ParkingLotMapConverter.dtoToEntity(parkingLotDTO.getParkingLotMap()))
                .build();
    }

}