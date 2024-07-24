package com.mvp.parkinglot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

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
}
