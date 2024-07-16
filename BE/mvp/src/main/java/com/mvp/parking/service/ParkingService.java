package com.mvp.parking.service;

import com.mvp.parking.dto.EnterDto;
import com.mvp.parking.entity.ParkedVehicle;
import com.mvp.parking.entity.ParkingLot;
import com.mvp.parking.entity.ParkingLotSpot;
import com.mvp.parking.repository.ParkedVehicleRepository;
import com.mvp.parking.repository.ParkingLotRepository;
import com.mvp.parking.repository.ParkingLotSpotRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
@AllArgsConstructor
public class ParkingService {

    private final ParkedVehicleRepository parkedVehicleRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotSpotRepository parkingLotSpotRepository;

    @Transactional
    public void enter(@RequestBody EnterDto enterDto){
        //주차칸을 찾는다.
        ParkingLotSpot parkingLotSpot = parkingLotSpotRepository.findByParkingLot_Id(enterDto.getParkingLotId());
        //주차칸 정보를 갱신한다.
        if(parkingLotSpot.getStatus() == 1){
            // 예외 발생
        }
        else{
            // 주차 차량 insert
            ParkedVehicle parkedVehicle = ParkedVehicle.builder()
                    .licensePlate(enterDto.getLicensePlate())
                    .image(enterDto.image).build();
            parkedVehicleRepository.save(parkedVehicle);

            // 주차 칸 update
            parkingLotSpot.setStatus(1);
            parkingLotSpot.setParkedVehicle(parkedVehicle);
        }

    }

}
