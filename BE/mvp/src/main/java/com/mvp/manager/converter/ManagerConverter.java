package com.mvp.manager.converter;

import com.mvp.manager.dto.ManagerDTO;
import com.mvp.manager.entity.Manager;

public class ManagerConverter {
    public ManagerDTO entityToDto(Manager manager) {
        return ManagerDTO.builder()
                .name(manager.getName())
                .email(manager.getEmail())
                .phoneNumber(manager.getPhoneNumber())
                .build();
    }

    public Manager dtoToEntity(ManagerDTO managerDTO) {
        return Manager.builder()
                .name(managerDTO.getName())
                .email(managerDTO.getEmail())
                .phoneNumber(managerDTO.getPhoneNumber())
                .build();
    }
}