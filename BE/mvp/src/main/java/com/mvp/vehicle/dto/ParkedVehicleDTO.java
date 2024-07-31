package com.mvp.vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkedVehicleDTO {

    private Long id;
    private LocalDateTime entranceTime;
    private byte[] image;
    private String licensePlate;
}
