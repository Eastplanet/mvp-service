package com.mvp.vehicle.entity;

import com.mvp.parkinglot.entity.ParkingLot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "enroll_vehicle")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id", nullable = false)
    private ParkingLot parkingLot;

    private String licencePlate;

    private Timestamp endDate;

    private String phoneNumber;
}
