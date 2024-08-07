package com.mvp.stats.dto;

import lombok.Data;

import java.util.List;

@Data
public class RevenueResDTO {
    private List<DailyRevenue> dailyRevenues;
    private List<MonthlyRevenue> monthlyRevenues;
    private Integer totalRevenue;
    private Integer totalMembershipsRevenue;
    private Double usingTimeAvg;
    private Double revenueAvg;
}
