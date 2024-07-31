package com.mvp.parkingbot.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "parking_bot")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingBot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number")
    private Integer serialNumber;

    @Column(name = "status")
    private Integer status;
}
