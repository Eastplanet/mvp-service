package com.mvp.vehicle.service;

import com.mvp.vehicle.converter.ParkedVehicleConverter;
import com.mvp.vehicle.dto.ParkedVehicleDTO;
import com.mvp.vehicle.entity.ParkedVehicle;
import com.mvp.vehicle.repository.ParkedVehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VehicleService {
    private final ParkedVehicleRepository parkedVehicleRepository;

    /**
     * 주차된 차량 정보 조회
     * @param vehicleId
     * @return
     */
    public ParkedVehicleDTO getParkedVehicle(Long vehicleId) {
        ParkedVehicle parkedVehicle = parkedVehicleRepository.findById(vehicleId).orElse(null);

        if(parkedVehicle != null){
            return ParkedVehicleConverter.entityToDto(parkedVehicle);
        } else{
            return null;
        }
    }

    /**
     * 주차된 차량 목록 조회
     * @return
     */
    public List<ParkedVehicleDTO> getParkedVehicleList() {
        List<ParkedVehicle> parkedVehicleList = parkedVehicleRepository.findAll();

        if(!parkedVehicleList.isEmpty()){
            return ParkedVehicleConverter.entityListToDtoList(parkedVehicleList);
        } else {
            return null;
        }
    }

    /**
     * 주차된 차량 번호로 조회
     * @param backNum
     * @return
     */
    public List<ParkedVehicleDTO> getParkedVehicleListByBackNum(String backNum) {
        List<ParkedVehicle> parkedVehicleList = parkedVehicleRepository.findByLicensePlateEndingWith(backNum);

        if(!parkedVehicleList.isEmpty()){
            return ParkedVehicleConverter.entityListToDtoList(parkedVehicleList);
        } else {
            return null;
        }
    }
}
