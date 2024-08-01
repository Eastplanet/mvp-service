package com.mvp.logs.repository;

import com.mvp.logs.entity.VehicleLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VehicleLogRepository extends JpaRepository<VehicleLog, Long> {
    List<VehicleLog> findAllByEntranceTimeBetween(LocalDateTime start, LocalDateTime end);
    List<VehicleLog> findAllByExitTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT v FROM VehicleLog v WHERE v.licensePlate like %:licensePlate%")
    List<VehicleLog> findByLicensePlate(LocalDateTime start, LocalDateTime end ,String licensePlate);
}
