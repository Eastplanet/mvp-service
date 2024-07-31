package com.mvp.membership.controller;

import com.mvp.membership.dto.MembershipDTO;
import com.mvp.membership.service.MembershipService;
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
    public ResponseEntity<MembershipDTO> createMembership(@RequestBody MembershipDTO membershipDTO) {
        MembershipDTO createdMembership = membershipService.createMembership(membershipDTO);

        if (createdMembership != null) {
            return ResponseEntity.ok(createdMembership);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{licensePlate}")
    public ResponseEntity<Void> deleteMembership(@RequestParam String licensePlate) {
        boolean success = membershipService.deleteMembership(licensePlate);

        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{membershipId}")
    public ResponseEntity<MembershipDTO> updateMembership(@RequestBody MembershipDTO membershipDTO) {
        MembershipDTO updatedMembership = membershipService.updateMembership(membershipDTO);

        if (updatedMembership != null) {
            return ResponseEntity.ok(updatedMembership);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{licensePlate}")
    public ResponseEntity<MembershipDTO> getMembership(@RequestParam String licensePlate) {
        MembershipDTO membership = membershipService.getMembership(licensePlate);

        if (membership != null) {
            return ResponseEntity.ok(membership);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<MembershipDTO>> getMembershipList(){
        List<MembershipDTO> membershipDTOList = membershipService.getMembershipList();

        if(membershipDTOList != null){
            return ResponseEntity.ok(membershipDTOList);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }
}
