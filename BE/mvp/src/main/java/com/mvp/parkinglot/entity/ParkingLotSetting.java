package com.mvp.parkinglot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "parking_lot_setting")
@Getter
@NoArgsConstructor
public class ParkingLotSetting {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    private int weekdayStartTime;

    private int weekdayEndTime;

    private int weekendStartTime;

    private int weekendEndTime;

    private int baseParkingTime;

    private int additionalUnitFee;

    private int additionalUnitTime;

    private String monthlyFee;
}
