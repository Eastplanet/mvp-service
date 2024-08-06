package com.mvp.logger.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleLogDTO {
    private String licensePlate;
    private LocalDateTime entranceTime;
    private LocalDateTime exitTime;
    private Long fee;
    private byte[] image;
    private Integer type;
}
