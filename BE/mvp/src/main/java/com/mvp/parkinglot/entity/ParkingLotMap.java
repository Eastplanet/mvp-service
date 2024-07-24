package com.mvp.parkinglot.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "parking_lot_map")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingLotMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image")
    @Lob
    private byte[] image;

    @Column(name = "map_info", columnDefinition = "TEXT")
    private String mapInfo;
}
