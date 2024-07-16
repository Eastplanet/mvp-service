package com.mvp.parking.repository;

import com.mvp.parking.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot,Integer> {
}
