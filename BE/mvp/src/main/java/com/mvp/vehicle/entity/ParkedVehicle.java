package com.mvp.vehicle.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "parked_vehicle")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkedVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entrance_time")
    private LocalDateTime entranceTime;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "license_plate", length = 255)
    private String licensePlate;
}
