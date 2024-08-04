package com.mvp.logger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExitLogDTO {
    private String licensePlate;
    private byte[] image;
    private Integer fee;
}
