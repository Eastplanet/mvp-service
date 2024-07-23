package com.mvp.parkingbot.repository;

import com.mvp.parkingbot.entity.ParkingBot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingBotRepository extends JpaRepository<ParkingBot, Long> {
}
