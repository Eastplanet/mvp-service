package com.mvp.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Long parkedVehicleId;
    private Integer parkingBotSerialNumber;
    private Integer start;
    private Integer end;
    private Integer type;
}
