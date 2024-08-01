package com.mvp.stats.service;

import com.mvp.logs.dto.VehicleLogDTO;
import com.mvp.logs.repository.VehicleLogRepository;
import com.mvp.logs.service.LogsService;
import com.mvp.parkinglot.dto.ParkingLotDTO;
import com.mvp.parkinglot.dto.ParkingLotSettingDTO;
import com.mvp.parkinglot.service.ParkingLotService;
import com.mvp.stats.dto.HomePageInitDto;
import com.mvp.stats.dto.ParkingLogReq;
import com.mvp.stats.dto.ParkingLogRes;
import com.mvp.stats.dto.ParkingLotSpotStats;
import com.mvp.stats.repository.StatsRepository;
import com.mvp.vehicle.dto.ParkedVehicleDTO;
import com.mvp.vehicle.dto.ParkingLotSpotDTO;
import com.mvp.vehicle.repository.ParkingLotSpotRepository;
import com.mvp.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final VehicleService vehicleService;
    private final LogsService logsService;
    private final ParkingLotService parkingLotService;

    public Integer calculatePrice(ParkedVehicleDTO parkedVehicleDTO) {
        ParkingLotSettingDTO setting = parkingLotService.getSetting();

        int price = setting.getBaseFee();

        LocalDateTime entranceTime = parkedVehicleDTO.getEntranceTime();
        LocalDateTime now = LocalDateTime.now();
        long minutes = Duration.between(entranceTime, now).toMinutes();
        minutes -= setting.getBaseParkingTime();

        if(minutes <= 0)return price;

        price += (int)(minutes/setting.getAdditionalUnitTime())*setting.getAdditionalUnitFee();
        return price;
    }

    public HomePageInitDto getInitHomePage() {
        LocalDateTime todayStart = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.now().with(LocalTime.MAX);

        HomePageInitDto homePageInitDto = new HomePageInitDto();

        List<VehicleLogDTO> enterCnt = logsService.findAllByEntranceTimeBetween(todayStart, todayEnd);
        homePageInitDto.setTodayIn(enterCnt.size());

        List<VehicleLogDTO> exitCnt = logsService.findAllByExitTimeBetween(todayStart, todayEnd);
        homePageInitDto.setTodayOut(exitCnt.size());

        int incomeSum = 0;
        for (VehicleLogDTO vehicleLogDTO : enterCnt) {
            if(vehicleLogDTO.getFee() != null) {
                incomeSum += vehicleLogDTO.getFee();
            }
        }
        homePageInitDto.setTodayIncome(incomeSum);

        List<ParkingLotSpotDTO> allParkingLotSpot = vehicleService.getAllParkingLotSpot();
        List<ParkingLotSpotStats> list = new ArrayList<>();

        ParkingLotSettingDTO parkingLotSetting = parkingLotService.getSetting();

        for (ParkingLotSpotDTO dto : allParkingLotSpot) {
            ParkingLotSpotStats stats = new ParkingLotSpotStats();
            stats.setParkingLotSpotNumber(dto.getSpotNumber());
            stats.setCarState(dto.getStatus());
            stats.setExitTime(null);
            if(dto.getParkedVehicle() != null){
                stats.setLicensePlate(dto.getParkedVehicle().getLicensePlate());
                stats.setParkingDate(dto.getParkedVehicle().getEntranceTime());
                stats.setEntranceTime(dto.getParkedVehicle().getEntranceTime());
                stats.setFee(calculatePrice(dto.getParkedVehicle()));
                stats.setImage(dto.getParkedVehicle().getImage());
            }
            list.add(stats);
        }

        homePageInitDto.setParkingLots(list);

        return homePageInitDto;
    }

    public List<ParkingLogRes> getParkingLot(ParkingLogReq parkingLogReq){
        List<VehicleLogDTO> find = logsService.findByEntranceTimeBetween(parkingLogReq.getStartDate(), parkingLogReq.getEndDate(), parkingLogReq.getSearchKeyword());

        List<ParkingLogRes> list = new ArrayList<>();
        for(VehicleLogDTO vehicleLogDTO : find){

            int state;
            if(vehicleLogDTO.getEntranceTime() != null){
                state = 1;
            }
            else{
                state = 0;
            }
            ParkingLogRes build = ParkingLogRes.builder()
                    .licensePlate(vehicleLogDTO.getLicensePlate())
                    .parkingDate(vehicleLogDTO.getEntranceTime())
                    .parkingState(state)
                    .entranceTime(vehicleLogDTO.getEntranceTime())
                    .exitTime(vehicleLogDTO.getExitTime())
                    .fee(vehicleLogDTO.getFee())
                    .image(vehicleLogDTO.getImage())
                    .build();
            list.add(build);
        }
        return list;
    }

}
