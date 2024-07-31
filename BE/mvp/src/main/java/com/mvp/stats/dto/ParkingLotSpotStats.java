package com.mvp.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLotSpotStats {
    private Integer parkingLotSpotNumber;
    private String carNumber;
    private LocalDateTime parkingDate;
    private Integer carState;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Integer fee;
    private byte[] imageBase64;
}
