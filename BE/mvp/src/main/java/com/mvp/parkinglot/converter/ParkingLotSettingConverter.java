package com.mvp.parkinglot.converter;


import com.mvp.parkinglot.dto.ParkingLotSettingDTO;
import com.mvp.parkinglot.entity.ParkingLotSetting;

/**
 * ParkingLotSetting의 Entity <-> DTO 변환을 위한 클래스입니다.
 */
public class ParkingLotSettingConverter {

    /**
     * @param parkingLotSetting 변환할 parkingLotSetting
     * @return if parkingLotSetting isNull, return null
     */
    public static ParkingLotSettingDTO entityToDto(ParkingLotSetting parkingLotSetting) {
        if (parkingLotSetting == null){
            return null;
        }
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

    /**
     * @param parkingLotSettingDTO 변환할 parkingLotSettingDTO
     * @return if parkingLotSettingDTO isNull, return null
     */
    public static ParkingLotSetting dtoToEntity(ParkingLotSettingDTO parkingLotSettingDTO) {
        if(parkingLotSettingDTO == null){
            return null;
        }
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
