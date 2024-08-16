package com.mvp.calculator.service;

import com.mvp.common.exception.RestApiException;
import com.mvp.common.exception.StatusCode;
import com.mvp.membership.service.MembershipService;
import com.mvp.parkinglot.dto.ParkingLotSettingDTO;
import com.mvp.parkinglot.service.ParkingLotService;
import com.mvp.vehicle.dto.ParkedVehicleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CalculatorService {
    private final MembershipService membershipService;
    private final ParkingLotService parkingLotService;

    public Long calculatePrice(ParkedVehicleDTO parkedVehicleDTO) {
        boolean ownMemberships = membershipService.isOwnMemberships(parkedVehicleDTO.getLicensePlate());
        if(ownMemberships){
            return 0L;
        }

        ParkingLotSettingDTO setting = parkingLotService.getSetting();

        long price = setting.getBaseFee();
        LocalDateTime entranceTime = parkedVehicleDTO.getEntranceTime();
        LocalDateTime now = LocalDateTime.now();
        long minutes = Duration.between(entranceTime, now).toMinutes();
        minutes -= setting.getBaseParkingTime();
        if(minutes > 0){
            if(setting.getAdditionalUnitTime() == 0){
                throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
            }
            price += (minutes/setting.getAdditionalUnitTime())*setting.getAdditionalUnitFee();
            if(parkedVehicleDTO.getDiscount() != null){
                price -= parkedVehicleDTO.getDiscount();
            }
            if(price < 0)price = 0;
        }
        return price;
    }
}
