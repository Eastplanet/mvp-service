package com.mvp.membership.controller;

import com.mvp.common.ResponseDto;
import com.mvp.common.exception.RestApiException;
import com.mvp.common.exception.StatusCode;
import com.mvp.membership.converter.MembershipConverter;
import com.mvp.membership.dto.MembershipDTO;
import com.mvp.membership.dto.MembershipResDTO;
import com.mvp.membership.service.MembershipService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
@AllArgsConstructor
public class MembershipController {
    private final MembershipService membershipService;

    @Transactional
    @PostMapping
    public ResponseEntity<ResponseDto> createMembership(@RequestBody MembershipDTO membershipDTO) {
        MembershipDTO createdMembership = membershipService.createMembership(membershipDTO);
        MembershipResDTO resp = new MembershipResDTO();

        if (createdMembership != null) {
            return ResponseDto.response(StatusCode.SUCCESS, createdMembership);
        } else {
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @DeleteMapping("/{licensePlate}")
    public ResponseEntity<ResponseDto> deleteMembership(@PathVariable String licensePlate) {
        boolean success = membershipService.deleteMembership(licensePlate);

        if (success) {
            return ResponseDto.response(StatusCode.SUCCESS, null);
        } else {
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @PatchMapping()
    public ResponseEntity<ResponseDto> updateMembership(@RequestBody MembershipDTO membershipDTO) {
        MembershipDTO updatedMembership = membershipService.updateMembership(membershipDTO);

        if(updatedMembership == null) {
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }
        MembershipResDTO result = MembershipConverter.dtoToResDTO(updatedMembership);
        return ResponseDto.response(StatusCode.SUCCESS, result);
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<ResponseDto> getMembership(@PathVariable String licensePlate) {
        MembershipDTO membership = membershipService.getMembership(licensePlate);
        MembershipResDTO result = MembershipConverter.dtoToResDTO(membership);

        if (result != null) {
            return ResponseDto.response(StatusCode.SUCCESS, result);
        } else {
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDto> getMembershipList() {
        List<MembershipDTO> membershipDTOList = membershipService.getMembershipList();
        List<MembershipResDTO> result = MembershipConverter.dtoListToResDTOList(membershipDTOList);
        return ResponseDto.response(StatusCode.SUCCESS, result);
    }
}
