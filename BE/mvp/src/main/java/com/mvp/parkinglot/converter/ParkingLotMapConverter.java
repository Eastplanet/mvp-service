package com.mvp.parkinglot.converter;

import com.mvp.parkinglot.dto.ParkingLotMapDTO;
import com.mvp.parkinglot.entity.ParkingLotMap;

/**
 * ParkingLotMap의 Entity <-> DTO 변환을 위한 클래스입니다.
 */
public class ParkingLotMapConverter  {

    /**
     * @param parkingLotMap 변환할 parkingLotMap
     * @return if parkingLotMap isNull, return null
     */
    public static ParkingLotMapDTO entityToDto(ParkingLotMap parkingLotMap) {
        if(parkingLotMap == null){
            return null;
        }
        return ParkingLotMapDTO.builder()
                .image(parkingLotMap.getImage())
                .mapInfo(parkingLotMap.getMapInfo())
                .build();
    }

    /**
     * @param parkingLotMapDto 변환할 parkingLotMapDto
     * @return if parkingLotMapDto isNull, return null
     */
    public static ParkingLotMap dtoToEntity(ParkingLotMapDTO parkingLotMapDto) {
        if(parkingLotMapDto == null){
            return null;
        }
        return ParkingLotMap.builder()
                .image(parkingLotMapDto.getImage())
                .mapInfo(parkingLotMapDto.getMapInfo())
                .build();
    }
}
