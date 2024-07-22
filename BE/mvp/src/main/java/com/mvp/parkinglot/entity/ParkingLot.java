package com.mvp.parkinglot.entity;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "parking_lot")
@Getter
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_setting_id", nullable = false)
    private ParkingLotSetting parkingLotSetting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_map_id", nullable = false)
    private ParkingLotMap parkingLotMap;
}
