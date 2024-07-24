package com.mvp.vehicle.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "parking_lot_spot")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingLotSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private Integer status;

    @Column(name = "spot_number")
    private Integer spotNumber;

    @ManyToOne
    @JoinColumn(name = "parked_vehicle_id", nullable = false)
    private ParkedVehicle parkedVehicle;
}
