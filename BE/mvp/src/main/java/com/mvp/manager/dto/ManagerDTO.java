package com.mvp.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDTO {
    private int id;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
}

