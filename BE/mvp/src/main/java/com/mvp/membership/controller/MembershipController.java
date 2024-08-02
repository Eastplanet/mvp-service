package com.mvp.membership.controller;

import com.mvp.common.ResponseDto;
import com.mvp.common.exception.RestApiException;
import com.mvp.common.exception.StatusCode;
import com.mvp.membership.dto.MembershipDTO;
import com.mvp.membership.service.MembershipService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/membership")
@AllArgsConstructor
public class MembershipController {
    private final MembershipService membershipService;

    @PostMapping
    public ResponseEntity<ResponseDto> createMembership(@RequestBody MembershipDTO membershipDTO) {
        MembershipDTO createdMembership = membershipService.createMembership(membershipDTO);

        if (createdMembership != null) {
            return ResponseDto.response(StatusCode.SUCCESS, createdMembership);
        } else {
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{licensePlate}")
    public ResponseEntity<ResponseDto> deleteMembership(@RequestParam String licensePlate) {
        boolean success = membershipService.deleteMembership(licensePlate);

        if (success) {
            return ResponseDto.response(StatusCode.SUCCESS, null);
        } else {
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{membershipId}")
    public ResponseEntity<ResponseDto> updateMembership(@RequestBody MembershipDTO membershipDTO) {
        MembershipDTO updatedMembership = membershipService.updateMembership(membershipDTO);

        if (updatedMembership != null) {
            return ResponseDto.response(StatusCode.SUCCESS, updatedMembership);
        } else {
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<ResponseDto> getMembership(@RequestParam String licensePlate) {
        MembershipDTO membership = membershipService.getMembership(licensePlate);

        if (membership != null) {
            return ResponseDto.response(StatusCode.SUCCESS, membership);
        } else {
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDto> getMembershipList() {
        List<MembershipDTO> membershipDTOList = membershipService.getMembershipList();

        if (membershipDTOList != null) {
            return ResponseDto.response(StatusCode.SUCCESS, membershipDTOList);
        } else {
            throw new RestApiException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }
}
