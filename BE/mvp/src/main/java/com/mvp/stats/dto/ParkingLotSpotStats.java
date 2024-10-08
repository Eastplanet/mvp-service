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
    private String licensePlate;
    private LocalDateTime parkingDate;
    private Integer carState;
    private Integer lotState;
    private LocalDateTime entranceTime;
    private LocalDateTime exitTime;
    private Long fee;
    private byte[] image;
}
