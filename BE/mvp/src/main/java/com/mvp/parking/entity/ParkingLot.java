package com.mvp.parking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity(name = "parking_lot")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLot {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String address;
}
