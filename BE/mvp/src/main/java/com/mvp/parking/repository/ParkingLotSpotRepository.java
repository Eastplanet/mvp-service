package com.mvp.parking.repository;

import com.mvp.parking.entity.ParkingLotSpot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingLotSpotRepository extends JpaRepository<ParkingLotSpot, Integer> {
    ParkingLotSpot findByParkingLot_Id(int id);
}
