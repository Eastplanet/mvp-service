package com.mvp.parkinglot.repository;

import com.mvp.parkinglot.entity.ParkingLotManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingLotManagerRepository extends JpaRepository<ParkingLotManager, Integer> {
    List<ParkingLotManager> findByManagerId(int managerId);
}
