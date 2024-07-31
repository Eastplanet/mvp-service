package com.mvp.vehicle.repository;

import com.mvp.vehicle.entity.ParkedVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkedVehicleRepository extends JpaRepository<ParkedVehicle, Long> {
    ParkedVehicle findByLicensePlate(String licensePlate);

    List<ParkedVehicle> findByLicensePlateEndingWith(String backNum);
}
