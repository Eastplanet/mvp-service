package com.mvp.parkingbot.dto;

import lombok.Data;

@Data
public class MoveVehicleRequestDTO {
    private String licensePlate;
    private Integer endSpot;
}
