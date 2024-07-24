package com.mvp.parkinglot.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;

@Entity
@Table(name = "parking_lot_setting")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingLotSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weekday_start_time")
    private LocalTime weekdayStartTime;

    @Column(name = "weekday_end_time")
    private LocalTime weekdayEndTime;

    @Column(name = "weekend_start_time")
    private LocalTime weekendStartTime;

    @Column(name = "weekend_end_time")
    private LocalTime weekendEndTime;

    @Column(name = "base_parking_time")
    private Integer baseParkingTime;

    @Column(name = "base_fee")
    private Integer baseFee;

    @Column(name = "additional_unit_fee")
    private Integer additionalUnitFee;

    @Column(name = "additional_unit_time")
    private Integer additionalUnitTime;

    @Column(name = "daily_fee")
    private Integer dailyFee;

    @Column(name = "weekly_fee")
    private Integer weeklyFee;

    @Column(name = "monthly_fee")
    private Integer monthlyFee;
}
