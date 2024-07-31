package com.mvp.common.exception;

import com.mvp.common.ResponseDto;
import com.mvp.parkinglot.dto.ParkingLotDTO;
import com.mvp.parkinglot.dto.ParkingLotMapDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/exception")
public class ExceptionTestController {
    
    @GetMapping("/no/{msg}")
    public ResponseEntity<ResponseDto> noSuch(@PathVariable(name = "msg") String msg){
        if(msg.equals("error")){
            throw new RestApiException(StatusCode.NO_SUCH_ELEMENT);
        }
        else{

            ParkingLotMapDTO map = ParkingLotMapDTO.builder()
                    .mapInfo("맵인포테스트")
                    .build();
            ParkingLotDTO test = ParkingLotDTO.builder()
                    .name("test")
                    .parkingLotMap(map)
                    .build();


            return ResponseDto.response(StatusCode.SUCCESS,test);
        }
    }
}
