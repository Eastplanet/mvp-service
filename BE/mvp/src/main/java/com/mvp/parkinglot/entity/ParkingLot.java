package com.mvp.parkinglot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "parking_lot")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_setting_id", nullable = true)
    private ParkingLotSetting parkingLotSetting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_map_id", nullable = true)
    private ParkingLotMap parkingLotMap;
}
