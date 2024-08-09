package com.mvp.vehicle.service;

import com.mvp.calculator.service.CalculatorService;
import com.mvp.vehicle.converter.ParkedVehicleConverter;
import com.mvp.vehicle.converter.ParkingLotSpotConverter;
import com.mvp.vehicle.dto.DiscountDTO;
import com.mvp.vehicle.dto.ParkedVehicleDTO;
import com.mvp.vehicle.dto.ParkedVehicleSettleDTO;
import com.mvp.vehicle.dto.ParkingLotSpotDTO;
import com.mvp.vehicle.entity.ParkedVehicle;
import com.mvp.vehicle.entity.ParkingLotSpot;
import com.mvp.vehicle.repository.ParkedVehicleRepository;
import com.mvp.vehicle.repository.ParkingLotSpotRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final ParkedVehicleRepository parkedVehicleRepository;
    private final ParkingLotSpotRepository parkingLotSpotRepository;
    private final CalculatorService calculatorService;

    /**
     * 주차된 차량 정보 조회
     *
     * @param vehicleId
     * @return
     */
    public ParkedVehicleDTO getParkedVehicle(Long vehicleId) {
        ParkedVehicle parkedVehicle = parkedVehicleRepository.findById(vehicleId).orElse(null);

        if (parkedVehicle != null) {
            return ParkedVehicleConverter.entityToDto(parkedVehicle);
        } else {
            return null;
        }
    }

    /**
     * 주차된 차량 목록 조회
     *
     * @return
     */
    public List<ParkedVehicleDTO> getParkedVehicleList() {
        List<ParkedVehicle> parkedVehicleList = parkedVehicleRepository.findAll();

        if (!parkedVehicleList.isEmpty()) {
            return ParkedVehicleConverter.entityListToDtoList(parkedVehicleList);
        } else {
            return null;
        }
    }

    /**
     * 주차된 차량 번호로 조회
     *
     * @param backNum
     * @return
     */
    public List<ParkedVehicleSettleDTO> getParkedVehicleListByBackNum(String backNum) {
        List<ParkedVehicle> parkedVehicleList = parkedVehicleRepository.findByLicensePlateEndingWith(backNum);

        if (!parkedVehicleList.isEmpty()) {
            List<ParkedVehicleSettleDTO> result = new ArrayList<>();
            for(ParkedVehicle parkedVehicle : parkedVehicleList){
                ParkedVehicleDTO parkedVehicleDTO = ParkedVehicleConverter.entityToDto(parkedVehicle);
                Long fee = calculatorService.calculatePrice(parkedVehicleDTO);

                ParkedVehicleSettleDTO parkedVehicleSettleDTO = new ParkedVehicleSettleDTO();
                parkedVehicleSettleDTO.setParkedVehicleDTO(parkedVehicleDTO, fee);

                result.add(parkedVehicleSettleDTO);
            }
            return result;
        } else {
            return null;
        }
    }

    public List<ParkingLotSpotDTO> getAllParkingLotSpot() {
        List<ParkingLotSpot> all = parkingLotSpotRepository.findAll();
        List<ParkingLotSpotDTO> result = ParkingLotSpotConverter.entityListToDtoList(all);
        return result;
    }

    @Transactional
    public ParkedVehicleDTO giveDiscount(DiscountDTO discountDTO) {
        ParkedVehicle parkedVehicle = parkedVehicleRepository.findByLicensePlate(discountDTO.getLicensePlate());

        if (parkedVehicle != null) {
            parkedVehicle.updateDiscount(discountDTO.getDiscountAmount());
            return ParkedVehicleConverter.entityToDto(parkedVehicle);
        } else {
            return null;
        }
    }

    @Transactional
    public void updateVehicle(List<ParkedVehicleDTO> list) {
        for(ParkedVehicleDTO parkedVehicleDTO : list){
            ParkedVehicle find = parkedVehicleRepository.findByLicensePlate(parkedVehicleDTO.getLicensePlate());
            find.updateImage(parkedVehicleDTO.getImage());

        }
    }
}
