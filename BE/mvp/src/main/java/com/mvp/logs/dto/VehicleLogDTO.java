package com.mvp.logs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleLogDTO {
    private String licensePlate;
    private LocalDateTime entranceTime;
    private LocalDateTime exitTime;
    private Integer fee;
}
