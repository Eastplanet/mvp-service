package com.mvp.parkinglot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "parking_lot_map")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingLotMap {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @Lob
    private byte[] image;

    @Lob
    private String mapInfo;
}
