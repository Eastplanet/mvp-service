package com.mvp.vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingLotSpotDTO {
    private Integer status;
    private Integer spotNumber;
    private ParkedVehicleDTO parkedVehicle;
}
