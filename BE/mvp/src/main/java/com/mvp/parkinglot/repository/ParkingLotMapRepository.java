package com.mvp.parkinglot.repository;

import com.mvp.parkinglot.entity.ParkingLotMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotMapRepository extends JpaRepository<ParkingLotMap, Long> {
}
