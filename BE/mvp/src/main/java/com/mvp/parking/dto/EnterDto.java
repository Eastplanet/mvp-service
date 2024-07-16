package com.mvp.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterDto {

    public int parkingLotId;
    public String licensePlate;
    public int parkingSpot;
    public String image;
}
