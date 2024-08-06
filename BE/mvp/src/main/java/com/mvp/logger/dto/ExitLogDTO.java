package com.mvp.logger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExitLogDTO {
    private String licensePlate;
    private byte[] image;
    private Long fee;
    private LocalDateTime entranceTime;
}
