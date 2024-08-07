package com.mvp.vehicle.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(name = "entrance_time", nullable = false)
    private LocalDateTime entranceTime;

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB", nullable = false)
    private byte[] image;

    @Column(name = "license_plate", length = 255, nullable = false)
    private String licensePlate;

    @Column(name = "discount")
    @ColumnDefault("0")
    private Integer discount;

    @Column(name = "status")
    @ColumnDefault("0")
    private Integer status;

    public void updateDiscount(Integer discount) {
        this.discount = discount;
    }

    public void updateStatus(Integer status) {
        this.status = status;
    }
}
