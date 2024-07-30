package com.mvp.parkingbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주차봇 DTO

 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingBotDTO {
    private int serialNumber;
    private int status;
}
