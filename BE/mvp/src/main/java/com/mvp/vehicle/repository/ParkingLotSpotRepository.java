package com.mvp.vehicle.repository;

import com.mvp.vehicle.entity.ParkingLotSpot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingLotSpotRepository extends JpaRepository<ParkingLotSpot, Long> {
    Optional<ParkingLotSpot> findFirstByStatus(int i);

    ParkingLotSpot findByParkedVehicleId(Long id);
}
