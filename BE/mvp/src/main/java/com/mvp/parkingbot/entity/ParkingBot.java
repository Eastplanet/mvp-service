package com.mvp.parkingbot.entity;

import com.mvp.parkinglot.entity.ParkingLot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "parking_bot")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingBot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id", nullable = false)
    private ParkingLot parkingLot;

    @Column(nullable = false)
    private int serialNumber;

    @Column(nullable = false)
    private int status;
}
