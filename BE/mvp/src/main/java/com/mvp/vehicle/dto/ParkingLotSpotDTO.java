package com.mvp.vehicle.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingLotSpotDTO {
    private Integer status;
    private Integer spotNumber;
    private ParkedVehicleDTO parkedVehicle;
}
