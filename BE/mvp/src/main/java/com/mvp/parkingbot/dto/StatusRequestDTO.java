package com.mvp.parkingbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusRequestDTO {
    private Integer serialNumber;
    private Integer status;
}
