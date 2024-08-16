package com.mvp.vehicle.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkedVehicleSettleDTO extends ParkedVehicleDTO {
    Long fee;

    public void setParkedVehicleDTO(ParkedVehicleDTO parkedVehicleDTO, Long fee) {
        this.setId(parkedVehicleDTO.getId());
        this.setEntranceTime(parkedVehicleDTO.getEntranceTime());
        this.setImage(parkedVehicleDTO.getImage());
        this.setLicensePlate(parkedVehicleDTO.getLicensePlate());
        this.setDiscount(parkedVehicleDTO.getDiscount());
        this.setStatus(parkedVehicleDTO.getStatus());
        this.fee = fee;
    }
}
