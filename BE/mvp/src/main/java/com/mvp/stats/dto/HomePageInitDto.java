package com.mvp.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomePageInitDto {
    private Integer todayIn;
    private Integer todayOut;
    private Integer todayIncome;
    private List<ParkingLotSpotStats> parkingLots;
}
