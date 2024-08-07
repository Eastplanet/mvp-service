package com.mvp.parkingbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주차봇 작업 DTO

 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Long parkedVehicleId;
    private Integer parkingBotSerialNumber;
    private Integer start;
    private Integer end;
    private Integer type;
}
