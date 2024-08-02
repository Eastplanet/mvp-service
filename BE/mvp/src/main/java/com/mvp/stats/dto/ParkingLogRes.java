package com.mvp.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLogRes {
    private String licensePlate;
    private LocalDateTime parkingDate;
    private Integer parkingState;
    private LocalDateTime entranceTime;
    private LocalDateTime exitTime;
    private Integer fee;
    private byte[] image;
}
