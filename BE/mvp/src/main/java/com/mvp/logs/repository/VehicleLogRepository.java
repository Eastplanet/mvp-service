package com.mvp.logs.repository;

import com.mvp.logs.entity.VehicleLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VehicleLogRepository extends JpaRepository<VehicleLog, Long> {
    List<VehicleLog> findAllByEntranceTimeBetween(LocalDateTime start, LocalDateTime end);
    List<VehicleLog> findAllByExitTimeBetween(LocalDateTime start, LocalDateTime end);
}
