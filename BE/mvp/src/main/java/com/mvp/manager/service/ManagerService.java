package com.mvp.manager.service;

import com.mvp.manager.converter.ManagerConverter;
import com.mvp.manager.dto.ManagerDTO;
import com.mvp.manager.entity.Manager;
import com.mvp.manager.repository.ManagerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ManagerService {

    private ManagerRepository managerRepository;

    public ManagerDTO createManager(ManagerDTO managerDTO) {
        Manager manager = ManagerConverter.toEntity(managerDTO);
        Manager savedManager = managerRepository.save(manager);
        return ManagerConverter.toDTO(savedManager);
    }


    public boolean deleteManager(String email) {
        Manager manager = managerRepository.findByEmail(email).orElse(null);

        if (manager != null) {
            managerRepository.delete(manager);
            return true;
        }

        return false;
    }

    public ManagerDTO login(ManagerDTO managerDTO) {
        Manager manager = managerRepository.findByEmail(managerDTO.getEmail()).orElse(null);

        if (manager != null && manager.getPassword().equals(managerDTO.getPassword())) {
            return ManagerConverter.toDTO(manager);
        }

        return null;
    }

    public ManagerDTO updateManager(ManagerDTO managerDTO) {
        Manager manager = managerRepository.findByEmail(managerDTO.getEmail()).orElse(null);

        if (manager != null) {
            Manager updatedManager = manager.toBuilder()
                    .name(managerDTO.getName() != null ? managerDTO.getName() : manager.getName())
                    .password(managerDTO.getPassword() != null ? managerDTO.getPassword() : manager.getPassword())  // 실제로는 비밀번호 암호화 필요
                    .email(managerDTO.getEmail() != null ? managerDTO.getEmail() : manager.getEmail())
                    .phoneNumber(managerDTO.getPhoneNumber() != null ? managerDTO.getPhoneNumber() : manager.getPhoneNumber())
                    .build();

            updatedManager = managerRepository.save(updatedManager);

            return ManagerConverter.toDTO(updatedManager);
        } else{
            return null;
        }

    }
}

