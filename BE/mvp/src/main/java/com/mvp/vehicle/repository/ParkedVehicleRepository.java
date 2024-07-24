package com.mvp.vehicle.repository;

import com.mvp.vehicle.entity.ParkedVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkedVehicleRepository extends JpaRepository<ParkedVehicle, Long> {
}
