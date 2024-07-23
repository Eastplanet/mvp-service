package com.mvp.parkingbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingBotDTO {
    private Long id;
    private int serialNumber;
    private int status;
    private Long parkingLotId;
}
