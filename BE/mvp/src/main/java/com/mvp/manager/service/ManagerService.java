package com.mvp.manager.service;

import com.mvp.manager.converter.ManagerConverter;
import com.mvp.manager.dto.ManagerDTO;
import com.mvp.manager.entity.Manager;
import com.mvp.manager.repository.ManagerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ManagerService {

    private ManagerRepository managerRepository;

    public List<ManagerDTO> getAllManagers() {
        return managerRepository.findAll().stream()
                .map(ManagerConverter::toDTO)
                .collect(Collectors.toList());
    }

    public ManagerDTO findManagerById(int id) {
        return managerRepository.findById(id)
                .map(ManagerConverter::toDTO)
                .orElse(null);
    }

    public ManagerDTO createManager(ManagerDTO managerDTO){
        Manager manager = ManagerConverter.toEntity(managerDTO);
        Manager savedManager = managerRepository.save(manager);
        return ManagerConverter.toDTO(savedManager);
    }

    public ManagerDTO updateManager(Integer id, ManagerDTO newManagerDTO) {
        ManagerDTO managerDTO = findManagerById(id);
        if (managerDTO != null) {
            Manager manager = ManagerConverter.toEntity(managerDTO);
            manager.update(ManagerConverter.toEntity(newManagerDTO));
            Manager savedManager = managerRepository.save(manager);
            return ManagerConverter.toDTO(savedManager);
        } else{
            return null;
        }
    }


    public void deleteManager(Integer id) {
        managerRepository.deleteById(id);
    }
}
