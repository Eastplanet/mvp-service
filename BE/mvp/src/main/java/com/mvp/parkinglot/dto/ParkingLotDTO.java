package com.mvp.parkinglot.dto;

import com.mvp.parkinglot.entity.ParkingLotMap;
import com.mvp.parkinglot.entity.ParkingLotSetting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotDTO {
    private String name;
    private String address;
    private ParkingLotSettingDTO parkingLotSetting;
    private ParkingLotMapDTO parkingLotMap;
}
