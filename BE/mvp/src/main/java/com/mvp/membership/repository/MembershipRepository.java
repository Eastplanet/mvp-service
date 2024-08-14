package com.mvp.membership.repository;

import com.mvp.logger.entity.VehicleLog;
import com.mvp.membership.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    boolean existsByLicensePlate(String licensePlate);
    void deleteByLicensePlate(String licensePlate);
    Membership findByLicensePlate(String licensePlate);

    @Query("SELECT m FROM Membership m WHERE m.startDate >= :start AND m.startDate < :end")
    List<Membership> findByLicensePlateEntranceTimeBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
