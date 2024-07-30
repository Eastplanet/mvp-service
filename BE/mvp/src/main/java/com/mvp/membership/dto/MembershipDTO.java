package com.mvp.membership.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipDTO {

    private String licencePlate;
    private LocalDateTime endDate;
    private String phoneNumber;
    private String name;
}