package com.mvp.vehicle.repository;

import com.mvp.vehicle.entity.ParkingLotSpot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotSpotRepository extends JpaRepository<ParkingLotSpot, Long> {
}
