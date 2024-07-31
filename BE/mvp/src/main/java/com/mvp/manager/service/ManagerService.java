package com.mvp.manager.service;

import com.mvp.common.HashUtil;
import com.mvp.common.exception.RestApiException;
import com.mvp.common.exception.StatusCode;
import com.mvp.manager.converter.ManagerConverter;
import com.mvp.manager.dto.ManagerDTO;
import com.mvp.manager.entity.Manager;
import com.mvp.manager.repository.ManagerRepository;
import com.mvp.parkinglot.dto.ParkingLotDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;

    public ManagerDTO createManager(ManagerDTO managerDTO) {
        if(checkDuplicateEmail(managerDTO)) throw new RestApiException(StatusCode.DUPLICATE_EMAIL);
        managerDTO.setPassword(HashUtil.sha256(managerDTO.getPassword()));
        Manager manager = ManagerConverter.dtoToEntity(managerDTO);
        Manager save = managerRepository.save(manager);
        return ManagerConverter.entityToDto(save);
    }

    public ManagerDTO login(ManagerDTO managerDTO) {
        Optional<Manager> find = managerRepository.findByEmail(managerDTO.getEmail());
        if(find.isEmpty()) throw new RestApiException(StatusCode.INVALID_EMAIL_OR_PASSWORD);
        Manager manager = find.get();

        if(manager.getPassword().equals(HashUtil.sha256(managerDTO.getPassword()))) {
            return ManagerConverter.entityToDto(manager);
        }
        else{
            throw new RestApiException(StatusCode.INVALID_EMAIL_OR_PASSWORD);
        }
    }

    public ManagerDTO updateManager(ManagerDTO managerDTO) {
        Optional<Manager> find = managerRepository.findByEmail(managerDTO.getEmail());
        if(find.isEmpty()) throw new RestApiException(StatusCode.NO_SUCH_ELEMENT);
        managerDTO.setPassword(HashUtil.sha256(managerDTO.getPassword()));

        Manager manager = find.get();
        manager.update(managerDTO);
        Manager result = managerRepository.save(manager);

        if(result == null) throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        return ManagerConverter.entityToDto(result);
    }

    public boolean checkDuplicateEmail(ManagerDTO managerDTO) {

        Optional<Manager> find = managerRepository.findByEmail(managerDTO.getEmail());
        if (find.isPresent()) {
            return true;
        }
        return false;
    }

    public boolean deleteManager(ManagerDTO managerDTO) {

        Optional<Manager> find = managerRepository.findByEmail(managerDTO.getEmail());
        if(find.isEmpty()) {
            throw new RestApiException(StatusCode.NO_SUCH_ELEMENT);
        }
        else{
            managerRepository.delete(find.get());
            return true;
        }
    }
}

