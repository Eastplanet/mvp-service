package com.mvp.manager.controller;

import com.mvp.common.ResponseDto;
import com.mvp.common.exception.RestApiException;
import com.mvp.common.exception.StatusCode;
import com.mvp.manager.dto.ManagerDTO;
import com.mvp.manager.entity.Manager;
import com.mvp.manager.service.ManagerService;
import com.mvp.parkinglot.dto.ParkingLotDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping()
    public ResponseEntity<ResponseDto> createManager(@RequestBody(required = true) ManagerDTO managerDTO) {
        if(managerDTO.getName() == null ||
           managerDTO.getEmail()== null ||
           managerDTO.getPhoneNumber()==null ||
           managerDTO.getPassword()==null) {
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }


        ManagerDTO manager = managerService.createManager(managerDTO);
        if(manager == null) {
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }

        return ResponseDto.response(StatusCode.SUCCESS, manager);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody ManagerDTO managerDTO) {
        if(managerDTO.getEmail() == null ||
        managerDTO.getPassword()==null) {
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }

        ManagerDTO login = managerService.login(managerDTO);
        login.setPassword(null);
        return ResponseDto.response(StatusCode.SUCCESS, login);
    }

    @PutMapping("/{email}")
    public ResponseEntity<ResponseDto> updateManager(@PathVariable String email, @RequestBody ManagerDTO managerDTO) {
        managerDTO.setEmail(email);
        if(managerDTO.getPassword() == null ||
            managerDTO.getName() == null ||
            managerDTO.getPhoneNumber() == null) {
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }

        ManagerDTO result = managerService.updateManager(managerDTO);
        if(result == null) {
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }

        result.setPassword(null);
        return ResponseDto.response(StatusCode.SUCCESS, result);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<ResponseDto> deleteManager(@RequestBody ManagerDTO managerDTO) {
        if(managerDTO.getEmail() == null) {
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }

        boolean result = managerService.deleteManager(managerDTO);
        if(result){
            return ResponseDto.response(StatusCode.SUCCESS,null);
        }
        else{
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

}
