package com.mvp.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ManagerDTO {
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
}

