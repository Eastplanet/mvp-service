package com.mvp.logger.repository;

import com.mvp.logger.entity.VehicleLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VehicleLogRepository extends JpaRepository<VehicleLog, Long> {
    List<VehicleLog> findAllByEntranceTimeBetween(LocalDateTime start, LocalDateTime end);
    List<VehicleLog> findAllByExitTimeBetween(LocalDateTime start, LocalDateTime end);

//    @Query("SELECT v FROM VehicleLog v WHERE v.licensePlate like %:licensePlate%")
//    List<VehicleLog> findByLicensePlateEntranceTimeBetween(LocalDateTime start, LocalDateTime end ,@Param("licensePlate") String licensePlate);

    @Query("SELECT v FROM VehicleLog v WHERE v.entranceTime BETWEEN :start AND :end AND v.licensePlate like %:licensePlate%")
    List<VehicleLog> findByLicensePlateEntranceTimeBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("licensePlate") String licensePlate);

}
