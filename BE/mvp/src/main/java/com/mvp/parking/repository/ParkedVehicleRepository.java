package com.mvp.parking.repository;

import com.mvp.parking.entity.ParkedVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkedVehicleRepository extends JpaRepository<ParkedVehicle, Integer> {
}

