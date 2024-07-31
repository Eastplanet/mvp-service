package com.mvp.manager.dto;

import lombok.*;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class ManagerDTO {
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
}

