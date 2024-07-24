package com.mvp.parkinglot.converter;


import com.mvp.parkinglot.dto.ParkingLotSettingDTO;
import com.mvp.parkinglot.entity.ParkingLotSetting;

public class ParkingLotSettingConverter {

    public ParkingLotSettingDTO entityToDto(ParkingLotSetting parkingLotSetting) {
        return ParkingLotSettingDTO.builder()
                .weekdayStartTime(parkingLotSetting.getWeekdayStartTime())
                .weekdayEndTime(parkingLotSetting.getWeekdayEndTime())
                .weekendStartTime(parkingLotSetting.getWeekendStartTime())
                .weekendEndTime(parkingLotSetting.getWeekendEndTime())
                .baseParkingTime(parkingLotSetting.getBaseParkingTime())
                .baseFee(parkingLotSetting.getBaseFee())
                .additionalUnitFee(parkingLotSetting.getAdditionalUnitFee())
                .additionalUnitTime(parkingLotSetting.getAdditionalUnitTime())
                .dailyFee(parkingLotSetting.getDailyFee())
                .weeklyFee(parkingLotSetting.getWeeklyFee())
                .monthlyFee(parkingLotSetting.getMonthlyFee())
                .build();
    }

    public ParkingLotSetting dtoToEntity(ParkingLotSettingDTO parkingLotSettingDTO) {
        return ParkingLotSetting.builder()
                .weekdayStartTime(parkingLotSettingDTO.getWeekdayStartTime())
                .weekdayEndTime(parkingLotSettingDTO.getWeekdayEndTime())
                .weekendStartTime(parkingLotSettingDTO.getWeekendStartTime())
                .weekendEndTime(parkingLotSettingDTO.getWeekendEndTime())
                .baseParkingTime(parkingLotSettingDTO.getBaseParkingTime())
                .baseFee(parkingLotSettingDTO.getBaseFee())
                .additionalUnitFee(parkingLotSettingDTO.getAdditionalUnitFee())
                .additionalUnitTime(parkingLotSettingDTO.getAdditionalUnitTime())
                .dailyFee(parkingLotSettingDTO.getDailyFee())
                .weeklyFee(parkingLotSettingDTO.getWeeklyFee())
                .monthlyFee(parkingLotSettingDTO.getMonthlyFee())
                .build();
    }
}
