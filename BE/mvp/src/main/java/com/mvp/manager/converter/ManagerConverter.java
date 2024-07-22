package com.mvp.manager.converter;

import com.mvp.manager.dto.ManagerDTO;
import com.mvp.manager.entity.Manager;

public class ManagerConverter {
    public static ManagerDTO toDTO(Manager manager) {
        return ManagerDTO.builder()
                .name(manager.getName())
                .password(manager.getPassword())
                .email(manager.getEmail())
                .phoneNumber(manager.getPhoneNumber())
                .build();
    }

    public static Manager toEntity(ManagerDTO managerDTO) {
        return Manager.builder()
                .name(managerDTO.getName())
                .password(managerDTO.getPassword())
                .email(managerDTO.getEmail())
                .phoneNumber(managerDTO.getPhoneNumber())
                .build();
    }
}
