package com.mvp.membership.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipDTO {
    private String licensePlate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String phoneNumber;
    private String name;
}