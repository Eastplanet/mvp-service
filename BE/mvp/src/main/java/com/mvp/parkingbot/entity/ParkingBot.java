package com.mvp.parkingbot.entity;

import com.mvp.parkinglot.entity.ParkingLot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "parking_bot")
@Getter
@NoArgsConstructor
public class ParkingBot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id", nullable = false)
    private ParkingLot parkingLot;

    private int serialNumber;

    private int status;
}
