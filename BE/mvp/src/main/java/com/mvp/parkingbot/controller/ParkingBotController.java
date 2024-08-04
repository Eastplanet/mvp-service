package com.mvp.parkingbot.controller;

import com.mvp.common.ResponseDto;
import com.mvp.common.exception.RestApiException;
import com.mvp.common.exception.StatusCode;
import com.mvp.parkingbot.dto.*;
import com.mvp.parkingbot.service.ParkingBotService;
import com.mvp.vehicle.dto.ParkedVehicleDTO;
import com.mvp.vehicle.repository.ParkedVehicleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/parking-bot")
@AllArgsConstructor
public class ParkingBotController {
    private ParkingBotService parkingBotService;

    /**
     * 입차 요청
     *
     * @param enterRequestDTO
     * @return
     */
    @PostMapping("/enter")
    public ResponseEntity<ResponseDto> enterRequest(@RequestBody EnterRequestDTO enterRequestDTO) {
        Task task = parkingBotService.handleEnterRequest(enterRequestDTO);
        if (task != null) {
            return ResponseDto.response(StatusCode.SUCCESS, task);
        } else {
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }
    }

    /**
     * 출차 요청
     *
     * @param licensePlate
     * @return
     */
    @DeleteMapping("/exit/{licensePlate}")
    public ResponseEntity<ResponseDto> exitRequest(@PathVariable String licensePlate) {
        boolean success = parkingBotService.handleExitRequest(licensePlate);
        if (success) {
            return ResponseDto.response(StatusCode.SUCCESS, null);
        } else {
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }
    }

    /**
     * 주차봇에게 할당된 작업을 가져옴
     *
     * @return
     */
    @GetMapping("/poll")
    public ResponseEntity<ResponseDto> pollTask() {
        Task task = parkingBotService.getTaskfromQueue();
        if (task != null) {
            parkingBotService.updateStatus(task.getParkingBotSerialNumber(), 1);
            return ResponseDto.response(StatusCode.SUCCESS, task);
        } else {
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }
    }

    /**
     * 임의 이동
     *
     * @param moveRequestDTO
     * @return
     */
    @PostMapping("/move")
    public ResponseEntity<ResponseDto> moveRequest(@RequestBody MoveRequestDTO moveRequestDTO) {
        Task task = parkingBotService.handleMoveRequest(moveRequestDTO);
        if (task != null) {
            return ResponseDto.response(StatusCode.SUCCESS, task);
        } else {
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }
    }

    /**
     * 차량 지정 이동
     *
     * @param moveVehicleRequestDTO
     * @return
     */
    @PostMapping("/move-vehicle")
    public ResponseEntity<ResponseDto> moveVehicle(@RequestBody MoveVehicleRequestDTO moveVehicleRequestDTO) {
        Task task = parkingBotService.handleMoveVehicleRequest(moveVehicleRequestDTO);
        if (task != null) {
            return ResponseDto.response(StatusCode.SUCCESS, task);
        } else {
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }
    }


    /**
     * 주차봇 상태 변경
     *
     * @param statusRequestDTO
     * @return
     */
    @PatchMapping("/status")
    public ResponseEntity<ResponseDto> updateStatus(@RequestBody StatusRequestDTO statusRequestDTO) {
        ParkingBotDTO parkingBotDTO = parkingBotService.updateStatus(statusRequestDTO.getSerialNumber(), statusRequestDTO.getStatus());

        if (parkingBotDTO != null) {
            return ResponseDto.response(StatusCode.SUCCESS, parkingBotDTO);
        } else {
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }
    }

    /**
     * 주차봇 전체 상태 변경
     *
     * @param status
     * @return
     */
    @PatchMapping("/status/all/{status}")
    public ResponseEntity<ResponseDto> updateAllStatus(@PathVariable Integer status) {
        List<ParkingBotDTO> parkingBotDTOList = parkingBotService.updateAllStatus(status);

        if (parkingBotDTOList != null) {
            return ResponseDto.response(StatusCode.SUCCESS, parkingBotDTOList);
        } else {
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }
    }

    /**
     * 주차봇 생성
     *
     * @param parkingBotDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<ResponseDto> createParkingBot(@RequestBody ParkingBotDTO parkingBotDTO) {
        ParkingBotDTO newParkingBotDTO = parkingBotService.createParkingBot(parkingBotDTO);

        if (newParkingBotDTO != null) {
            return ResponseDto.response(StatusCode.SUCCESS, newParkingBotDTO);
        } else {
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 주차봇 삭제
     *
     * @param serialNumber
     * @return
     */
    @DeleteMapping("/{serialNumber}")
    public ResponseEntity<ResponseDto> deleteParkingBot(@PathVariable Integer serialNumber) {
        boolean success = parkingBotService.deleteParkingBot(serialNumber);
        if (success) {
            return ResponseDto.response(StatusCode.SUCCESS, null);
        } else {
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 주차봇 리스트 조회
     *
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<ResponseDto> getParkingBotList() {
        List<ParkingBotDTO> parkingBotDTOList = parkingBotService.getParkingBotList();

        if (parkingBotDTOList != null) {
            return ResponseDto.response(StatusCode.SUCCESS, parkingBotDTOList);
        } else {
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }
}
