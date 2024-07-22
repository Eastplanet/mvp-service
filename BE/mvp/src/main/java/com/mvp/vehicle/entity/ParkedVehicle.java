package com.mvp.vehicle.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "parked_vehicle")
@Getter
@NoArgsConstructor
public class ParkedVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Timestamp entranceTime;

    @Lob
    private byte[] image;

    private String licensePlate;

    private int vehicleType;
}

