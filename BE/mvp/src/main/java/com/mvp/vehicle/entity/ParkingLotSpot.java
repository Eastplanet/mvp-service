package com.mvp.vehicle.entity;

import com.mvp.parkinglot.entity.ParkingLot;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "parking_lot_spot")
@Getter
@NoArgsConstructor
public class ParkingLotSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int status;

    private int spotNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id", nullable = false)
    private ParkingLot parkingLot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parked_vehicle_id", nullable = false)
    private ParkedVehicle parkedVehicle;
}
