package com.mvp.membership.service;

import com.mvp.membership.converter.MembershipConverter;
import com.mvp.membership.dto.MembershipDTO;
import com.mvp.membership.entity.Membership;
import com.mvp.membership.repository.MembershipRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MembershipService {
    private final MembershipRepository membershipRepository;

    public MembershipDTO createMembership(MembershipDTO membershipDTO) {
        Membership createdMembership = membershipRepository.save(MembershipConverter.dtoToEntity(membershipDTO));

        return MembershipConverter.entityToDto(createdMembership);
    }

    public boolean deleteMembership(String licensePlate) {
        if(membershipRepository.existsByLicensePlate(licensePlate)) {
            membershipRepository.deleteByLicensePlate(licensePlate);
            return true;
        } else {
            return false;
        }
    }

    public MembershipDTO updateMembership(MembershipDTO membershipDTO) {
        if(membershipRepository.existsByLicensePlate(membershipDTO.getLicensePlate())) {
            Membership updatedMembership = membershipRepository.save(MembershipConverter.dtoToEntity(membershipDTO));
            return MembershipConverter.entityToDto(updatedMembership);
        } else {
            return null;
        }
    }

    public MembershipDTO getMembership(String licensePlate) {
        Membership membership = membershipRepository.findByLicensePlate(licensePlate);

        if(membership != null) {
            return MembershipConverter.entityToDto(membership);
        } else {
            return null;
        }
    }

    public List<MembershipDTO> getMembershipList() {
        List<Membership> membershipList = membershipRepository.findAll();

        return MembershipConverter.entityToDtoList(membershipList);
    }
}
