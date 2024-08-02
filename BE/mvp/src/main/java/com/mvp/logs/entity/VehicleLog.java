package com.mvp.logs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "vehicle_log")
public class VehicleLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate", length = 16)
    private String licensePlate;

    @Column(name = "entrance_time")
    private LocalDateTime entranceTime;

    @Column(name = "exit_time")
    private LocalDateTime exitTime;

    @Column(name = "fee")
    private Integer fee;

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

}
