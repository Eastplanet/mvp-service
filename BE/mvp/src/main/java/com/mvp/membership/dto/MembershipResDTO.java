package com.mvp.membership.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipResDTO {
    private String licensePlate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String phoneNumber;
    private String name;
}
