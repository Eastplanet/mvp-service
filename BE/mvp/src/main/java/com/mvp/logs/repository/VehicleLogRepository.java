package com.mvp.logs.repository;

import com.mvp.logs.entity.VehicleLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleLogRepository extends JpaRepository<VehicleLog, Long> {
}
