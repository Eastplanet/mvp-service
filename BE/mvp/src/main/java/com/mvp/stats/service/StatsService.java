package com.mvp.stats.service;

import com.mvp.calculator.service.CalculatorService;
import com.mvp.common.exception.RestApiException;
import com.mvp.common.exception.StatusCode;
import com.mvp.logger.dto.VehicleLogDTO;
import com.mvp.logger.repository.VehicleLogRepository;
import com.mvp.logger.service.LoggerService;
import com.mvp.membership.dto.MembershipDTO;
import com.mvp.membership.service.MembershipService;
import com.mvp.parkinglot.dto.ParkingLotDTO;
import com.mvp.parkinglot.dto.ParkingLotSettingDTO;
import com.mvp.parkinglot.entity.ParkingLotSetting;
import com.mvp.parkinglot.repository.ParkingLotSettingRepository;
import com.mvp.parkinglot.service.ParkingLotService;
import com.mvp.stats.dto.*;
import com.mvp.stats.repository.StatsRepository;
import com.mvp.vehicle.dto.ParkedVehicleDTO;
import com.mvp.vehicle.dto.ParkingLotSpotDTO;
import com.mvp.vehicle.repository.ParkingLotSpotRepository;
import com.mvp.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final VehicleService vehicleService;
    private final LoggerService loggerService;
    private final ParkingLotService parkingLotService;
    private final MembershipService membershipService;
    private final CalculatorService calculateService;

    public HomePageInitDto getInitHomePage() {
        LocalDateTime todayStart = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.now().with(LocalTime.MAX);

        HomePageInitDto homePageInitDto = new HomePageInitDto();

        List<VehicleLogDTO> enterCnt = loggerService.findAllByEntranceTimeBetween(todayStart, todayEnd);
        homePageInitDto.setTodayIn(enterCnt.size());

        List<VehicleLogDTO> exitCnt = loggerService.findAllByExitTimeBetween(todayStart, todayEnd);
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

        for (ParkingLotSpotDTO dto : allParkingLotSpot) {
            ParkingLotSpotStats stats = new ParkingLotSpotStats();
            stats.setParkingLotSpotNumber(dto.getSpotNumber());
            stats.setLotState(dto.getStatus());
            stats.setExitTime(null);
            if(dto.getParkedVehicle() != null){
                stats.setLicensePlate(dto.getParkedVehicle().getLicensePlate());
                stats.setParkingDate(dto.getParkedVehicle().getEntranceTime());
                stats.setEntranceTime(dto.getParkedVehicle().getEntranceTime());
                stats.setFee(calculateService.calculatePrice(dto.getParkedVehicle()));
                stats.setImage(dto.getParkedVehicle().getImage());
                stats.setCarState(dto.getParkedVehicle().getStatus());
            }
            list.add(stats);
        }

        homePageInitDto.setParkingLots(list);

        return homePageInitDto;
    }

    public List<ParkingLogRes> getParkingLot(ParkingLogReq parkingLogReq){
        // getStartDate()를 해당 날짜의 00시로 설정
        LocalDateTime startDate = parkingLogReq.getStartDate().toLocalDate().atStartOfDay();
        // getEndDate()를 해당 날짜의 23시 59분으로 설정
        LocalDateTime endDate = parkingLogReq.getEndDate().toLocalDate().atTime(23, 59, 59);

        List<VehicleLogDTO> find = loggerService.findByEntranceTimeBetween(startDate, endDate, parkingLogReq.getLicensePlate());

        List<ParkingLogRes> list = new ArrayList<>();
        for(VehicleLogDTO vehicleLogDTO : find){

            int state;
            if(vehicleLogDTO.getType() == 1){
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

        list.sort((o1, o2) -> {
            // startTime 기준으로 정렬
            int startTimeComparison = o1.getEntranceTime().compareTo(o2.getEntranceTime());
            if (startTimeComparison != 0) {
                return startTimeComparison;
            }
            // startTime이 같은 경우 출차 시간 기준으로 정렬
            if (o1.getExitTime() == null && o2.getExitTime() == null) {
                return 0;
            }
            if (o1.getExitTime() == null) {
                return 1; // exitTime이 없는 경우 뒤로 보냄
            }
            if (o2.getExitTime() == null) {
                return -1; // exitTime이 없는 경우 뒤로 보냄
            }
            // 출차 시간이 둘 다 존재하면 내림차순 정렬
            return o2.getExitTime().compareTo(o1.getExitTime());
        });

        return list;
    }

    public RevenueResDTO getRevenueStats(){
        RevenueResDTO revenueResDTO = new RevenueResDTO();

        List<DailyRevenue> daily = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDateTime startOfDay = LocalDate.now().minusDays(i).atStartOfDay();
            LocalDateTime endOfDay = LocalDate.now().minusDays(i).atTime(LocalTime.MAX);
            List<VehicleLogDTO> dailyLogs = loggerService.findAllByExitTimeBetween(startOfDay, endOfDay);
            int sum = 0;
            int cnt = 0;
            for(VehicleLogDTO vehicleLogDTO : dailyLogs){
                if(vehicleLogDTO.getType() == 1){
                    sum += vehicleLogDTO.getFee();
                    cnt ++;
                }
            }
            DailyRevenue dailyRevenue = DailyRevenue
                    .builder()
                    .date(startOfDay)
                    .revenue(sum)
                    .parkingCount(cnt)
                    .build();
            daily.add(dailyRevenue);
        }
        revenueResDTO.setDailyRevenues(daily);



        // 이번 달을 포함한 지난 12개월 동안의 데이터를 가져오기 위한 리스트
        List<MonthlyRevenue> monthlyRevenues = new ArrayList<>();
        // 현재 날짜를 기준으로 지난 12개월 동안의 데이터를 가져옴
        for (int i = 11; i >= 0; i--) {
            // 현재 달에서 i 달 전의 첫째 날
            LocalDate firstDayOfMonth = LocalDate.now().minusMonths(i).withDayOfMonth(1);
            LocalDateTime startOfMonth = firstDayOfMonth.atStartOfDay();

            // 현재 달에서 i 달 전의 마지막 날
            LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());
            LocalDateTime endOfMonth = lastDayOfMonth.atTime(LocalTime.MAX);

            // 해당 월의 데이터를 가져옴
            List<VehicleLogDTO> monthlyLogs = loggerService.findAllByExitTimeBetween(startOfMonth, endOfMonth);
            int sum = 0;
            int cnt = 0;
            int usingTimeSum = 0;

            for (VehicleLogDTO vehicleLogDTO : monthlyLogs) {
                if (vehicleLogDTO.getType() == 1) {
                    sum += vehicleLogDTO.getFee();
                    cnt++;
                    // entranceTime과 exitTime의 차이를 분으로 계산하여 usingTimeSum에 더함
                    Duration duration = Duration.between(vehicleLogDTO.getEntranceTime(), vehicleLogDTO.getExitTime());
                    long minutes = (int)duration.toMinutes();
                    usingTimeSum += minutes;
                }
            }

            // MonthlyRevenue 객체를 생성하여 리스트에 추가
            MonthlyRevenue monthlyRevenue = MonthlyRevenue
                    .builder()
                    .date(startOfMonth) // 또는 firstDayOfMonth.getMonth()를 사용하여 월 정보만 저장할 수도 있음
                    .revenue(sum)
                    .parkingCount(cnt)
                    .build();
            monthlyRevenues.add(monthlyRevenue);

            if(i == 0){
                revenueResDTO.setTotalRevenue(sum);
                revenueResDTO.setRevenueAvg((double)sum/cnt);
                revenueResDTO.setTotalMembershipsRevenue(0);
                revenueResDTO.setUsingTimeAvg((double)usingTimeSum/cnt);

                List<MembershipDTO> membershipDate = membershipService.findMembershipDate(startOfMonth, endOfMonth);

                ParkingLotSettingDTO setting = parkingLotService.getSetting();


                revenueResDTO.setTotalMembershipsRevenue(membershipDate.size()*setting.getMonthlyFee());
            }
        }
        revenueResDTO.setMonthlyRevenues(monthlyRevenues);


        return revenueResDTO;
    }

}
