package com.mvp.parkingbot.controller;

import com.mvp.parkingbot.dto.*;
import com.mvp.parkingbot.service.ParkingBotService;
import com.mvp.vehicle.dto.ParkedVehicleDTO;
import com.mvp.vehicle.repository.ParkedVehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-bot")
@AllArgsConstructor
public class ParkingBotController {
    private ParkingBotService parkingBotService;

    /**
     * 입차 요청
     * @param enterRequestDTO
     * @return
     */
    @PostMapping("/enter")
    public ResponseEntity<Task> enterRequest(@RequestBody EnterRequestDTO enterRequestDTO) {
        Task task = parkingBotService.handleEnterRequest(enterRequestDTO);
        if(task != null){
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 출차 요청
     * @param licensePlate
     * @return
     */
    @DeleteMapping("/exit/{licensePlate}")
    public ResponseEntity<Void> exitRequest(@PathVariable String licensePlate) {
        boolean success = parkingBotService.handleExitRequest(licensePlate);
        if(success){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 주차봇에게 할당된 작업을 가져옴
     * @return
     */
    @GetMapping("/poll")
    public ResponseEntity<Task> pollTask(){
        Task task = parkingBotService.getTaskfromQueue();
        if(task != null){
            parkingBotService.updateStatus(task.getParkingBotSerialNumber(), 1);
            return ResponseEntity.ok(task);
        } else{
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * 임의 이동
     * @param moveRequestDTO
     * @return
     */
    @PostMapping("/move")
    public ResponseEntity<Task> moveRequest(@RequestBody MoveRequestDTO moveRequestDTO) {
        Task task = parkingBotService.handleMoveRequest(moveRequestDTO);
        if(task != null){
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 주차봇 상태 변경
     * @param statusRequestDTO
     * @return
     */
    @PatchMapping("/status")
    public ResponseEntity<ParkingBotDTO> updateStatus(@RequestBody StatusRequestDTO statusRequestDTO) {
        ParkingBotDTO parkingBotDTO = parkingBotService.updateStatus(statusRequestDTO.getSerialNumber(), statusRequestDTO.getStatus());

        if(parkingBotDTO != null){
            return ResponseEntity.ok(parkingBotDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 주차봇 전체 상태 변경
     * @param status
     * @return
     */
    @PatchMapping("/status/all/{status}")
    public ResponseEntity<List<ParkingBotDTO>> updateAllStatus(@PathVariable Integer status) {
        List<ParkingBotDTO> parkingBotDTOList = parkingBotService.updateAllStatus(status);

        if(parkingBotDTOList != null){
            return ResponseEntity.ok(parkingBotDTOList);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 주차봇 생성
     * @param parkingBotDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<ParkingBotDTO> createParkingBot(@RequestBody ParkingBotDTO parkingBotDTO) {
        ParkingBotDTO newParkingBotDTO = parkingBotService.createParkingBot(parkingBotDTO);

        if(newParkingBotDTO != null){
            return ResponseEntity.ok(newParkingBotDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 주차봇 삭제
     * @param serialNumber
     * @return
     */
    @DeleteMapping("/{serialNumber}")
    public ResponseEntity<Void> deleteParkingBot(@PathVariable Integer serialNumber) {
        boolean success = parkingBotService.deleteParkingBot(serialNumber);
        if(success){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 주차봇 리스트 조회
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<ParkingBotDTO>> getParkingBotList() {
        List<ParkingBotDTO> parkingBotDTOList = parkingBotService.getParkingBotList();

        if(parkingBotDTOList != null){
            return ResponseEntity.ok(parkingBotDTOList);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
