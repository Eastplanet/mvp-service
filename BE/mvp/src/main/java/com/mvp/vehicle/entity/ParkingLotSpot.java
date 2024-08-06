package com.mvp.vehicle.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "parking_lot_spot")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingLotSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    @ColumnDefault("0")
    private Integer status;

    @Column(name = "spot_number", nullable = false)
    private Integer spotNumber;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parked_vehicle_id", referencedColumnName = "id")
    private ParkedVehicle parkedVehicle;

    public void updateVehicleAndStatus(ParkedVehicle vehicle, Integer status){
        this.parkedVehicle = vehicle;
        this.status = status;
    }
}
