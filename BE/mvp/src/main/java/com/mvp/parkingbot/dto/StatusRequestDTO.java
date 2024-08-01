package com.mvp.parkingbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주차봇 상태 요청 DTO

 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusRequestDTO {
    private Integer serialNumber;
    private Integer status;
}
