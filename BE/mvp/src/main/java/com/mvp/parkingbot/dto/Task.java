package com.mvp.parkingbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Integer parkingBotSerialNumber;
    private Integer start;
    private Integer end;
}
