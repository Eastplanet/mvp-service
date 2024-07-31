package com.mvp.parkinglot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotSettingDTO {
    private LocalTime weekdayStartTime;
    private LocalTime weekdayEndTime;
    private LocalTime weekendStartTime;
    private LocalTime weekendEndTime;
    private Integer baseParkingTime;
    private Integer baseFee;
    private Integer additionalUnitFee;
    private Integer additionalUnitTime;
    private Integer dailyFee;
    private Integer weeklyFee;
    private Integer monthlyFee;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingLotSettingDTO that = (ParkingLotSettingDTO) o;
        return Objects.equals(weekdayStartTime, that.weekdayStartTime) && Objects.equals(weekdayEndTime, that.weekdayEndTime) && Objects.equals(weekendStartTime, that.weekendStartTime) && Objects.equals(weekendEndTime, that.weekendEndTime) && Objects.equals(baseParkingTime, that.baseParkingTime) && Objects.equals(baseFee, that.baseFee) && Objects.equals(additionalUnitFee, that.additionalUnitFee) && Objects.equals(additionalUnitTime, that.additionalUnitTime) && Objects.equals(dailyFee, that.dailyFee) && Objects.equals(weeklyFee, that.weeklyFee) && Objects.equals(monthlyFee, that.monthlyFee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weekdayStartTime, weekdayEndTime, weekendStartTime, weekendEndTime, baseParkingTime, baseFee, additionalUnitFee, additionalUnitTime, dailyFee, weeklyFee, monthlyFee);
    }
}
