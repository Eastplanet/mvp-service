package com.mvp.parking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "parked_vehicle")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkedVehicle {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column @NotNull
    private String licensePlate;

    @Column(columnDefinition = "TIMESTAMP") @NotNull @CreationTimestamp
    private LocalDateTime entranceTime;

    @Column @Lob @NotNull
    private String image;
}
