package com.mvp.manager.repository;

import com.mvp.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
}
