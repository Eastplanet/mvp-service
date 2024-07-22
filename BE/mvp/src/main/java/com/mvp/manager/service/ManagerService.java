package com.mvp.manager.service;

import com.mvp.manager.converter.ManagerConverter;
import com.mvp.manager.dto.ManagerDTO;
import com.mvp.manager.entity.Manager;
import com.mvp.manager.repository.ManagerRepository;
import com.mvp.parkinglot.dto.ParkingLotDTO;
import com.mvp.parkinglot.entity.ParkingLotManager;
import com.mvp.parkinglot.repository.ParkingLotManagerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ManagerService {

    private ManagerRepository managerRepository;
    private ParkingLotManagerRepository parkingLotManagerRepository;

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
        } else {
            return null;
        }

    }

    public List<ParkingLotDTO> getParkingLotsByManagerEmail(String email) {
        Manager manager = managerRepository.findByEmail(email).orElse(null);

        if (manager != null) {
            List<ParkingLotManager> parkingLotManagers = parkingLotManagerRepository.findByManagerId(manager.getId());

            return parkingLotManagers.stream()
                    .map(ParkingLotManager::getParkingLot)
                    .map(parkingLot -> ParkingLotDTO.builder()
                            .id(parkingLot.getId())
                            .name(parkingLot.getName())
                            .address(parkingLot.getAddress())
                            .build())
                    .collect(Collectors.toList());
        }

        return null;
    }

}

