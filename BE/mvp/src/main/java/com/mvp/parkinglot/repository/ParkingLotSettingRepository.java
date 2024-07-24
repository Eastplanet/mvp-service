package com.mvp.parkinglot.repository;

import com.mvp.parkinglot.entity.ParkingLotSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotSettingRepository extends JpaRepository<ParkingLotSetting, Long> {
}
