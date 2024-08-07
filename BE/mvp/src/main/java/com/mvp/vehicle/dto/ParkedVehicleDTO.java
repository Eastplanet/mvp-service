package com.mvp.vehicle.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkedVehicleDTO {

    private Long id;
    private LocalDateTime entranceTime;
    private byte[] image;
    private String licensePlate;
    private Integer discount;
    private Integer status;
}
