package com.mvp.vehicle.dto;

import lombok.Data;

@Data
public class DiscountDTO {
    private Long parkedVehicleId;
    private Integer discountAmount;
}
