package com.mvp.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthlyRevenue {
    private LocalDateTime date;
    private Integer revenue;
    private Integer parkingCount;
}
