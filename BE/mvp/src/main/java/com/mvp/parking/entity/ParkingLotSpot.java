package com.mvp.parking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity (name = "parking_lot_spot")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotSpot {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column @NotNull
    private int status;

    @Column @NotNull
    private int num;

    @OneToOne
    @JoinColumn(name = "parked_vehicle_id")
    private ParkedVehicle parkedVehicle;

    @ManyToOne
    @JoinColumn(name = "parking_lot_id")
    private ParkingLot parkingLot;
}
