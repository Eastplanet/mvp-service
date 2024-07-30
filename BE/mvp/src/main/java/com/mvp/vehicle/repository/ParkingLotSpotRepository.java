package com.mvp.vehicle.repository;

import com.mvp.vehicle.entity.ParkingLotSpot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingLotSpotRepository extends JpaRepository<ParkingLotSpot, Long> {
    ParkingLotSpot findByParkedVehicleId(Long id);
    Optional<ParkingLotSpot> findFirstByStatus(int status);
}
