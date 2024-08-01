package com.mvp.parkingbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주차봇 이동 요청 DTO

 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoveRequestDTO {
    private Integer start;
    private Integer end;
}
