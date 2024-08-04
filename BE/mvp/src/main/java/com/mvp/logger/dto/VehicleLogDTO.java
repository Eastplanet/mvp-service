package com.mvp.logger.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleLogDTO {
    private String licensePlate;
    private LocalDateTime entranceTime;
    private LocalDateTime exitTime;
    private Integer fee;
    private byte[] image;
    private Integer type;
}
