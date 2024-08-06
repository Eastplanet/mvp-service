package com.mvp.parkingbot.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(name = "serial_number", nullable = false)
    private Integer serialNumber;

    @Column(name = "status")
    @ColumnDefault("0")
    private Integer status;
}
