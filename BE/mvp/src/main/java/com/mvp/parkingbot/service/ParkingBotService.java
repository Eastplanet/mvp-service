package com.mvp.parkingbot.service;

import com.mvp.parkingbot.converter.ParkingBotConverter;
import com.mvp.parkingbot.dto.ParkingBotDTO;
import com.mvp.parkingbot.entity.ParkingBot;
import com.mvp.parkingbot.repository.ParkingBotRepository;
import com.mvp.parkinglot.entity.ParkingLot;
import com.mvp.parkinglot.repository.ParkingLotRepository;
import org.springframework.stereotype.Service;

@Service
public class ParkingBotService {
    private ParkingBotRepository parkingBotRepository;
    private ParkingLotRepository parkingLotRepository;

    /**
     * 주차 봇 등록
     * @param parkingBotDTO
     * @return
     */
    public ParkingBotDTO registerParkingBot(ParkingBotDTO parkingBotDTO) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingBotDTO.getParkingLotId()).orElse(null);

        if(parkingLot == null){
            return null;
        }

        ParkingBot savedParkingBot = parkingBotRepository.save(ParkingBotConverter.toEntity(parkingBotDTO, parkingLot));
        return ParkingBotConverter.toDto(savedParkingBot);
    }

    /**
     * 주차 봇 삭제
     * @param parkingBotId
     * @return
     */
    public boolean deleteParkingBot(Long parkingBotId){
        if (parkingBotRepository.existsById(parkingBotId)) {
            parkingBotRepository.deleteById(parkingBotId);
            return true;
        } else {
            return false;
        }
    }
}
