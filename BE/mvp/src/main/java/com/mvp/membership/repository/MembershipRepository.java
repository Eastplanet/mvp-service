package com.mvp.membership.repository;

import com.mvp.membership.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    boolean existsByLicensePlate(String licensePlate);
    void deleteByLicensePlate(String licensePlate);
    Membership findByLicensePlate(String licensePlate);
}
