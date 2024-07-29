package com.mvp.parkingbot.repository;

import com.mvp.parkingbot.entity.ParkingBot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingBotRepository extends JpaRepository<ParkingBot, Long> {
    Optional<ParkingBot> findFirstByStatus(int status);
}
