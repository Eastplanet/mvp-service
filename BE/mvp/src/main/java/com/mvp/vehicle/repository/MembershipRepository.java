package com.mvp.vehicle.repository;

import com.mvp.vehicle.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
}
