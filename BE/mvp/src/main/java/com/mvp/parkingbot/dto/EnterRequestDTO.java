package com.mvp.parkingbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterRequestDTO {
    private String licensePlate;
    private LocalDateTime entranceTime;
    private byte[] image;
}
