package com.mvp.stats.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParkingLogReq {
    private String searchKeyword;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
