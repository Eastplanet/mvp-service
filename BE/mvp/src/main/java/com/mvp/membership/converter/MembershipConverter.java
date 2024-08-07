package com.mvp.membership.converter;

import com.mvp.membership.dto.MembershipDTO;
import com.mvp.membership.dto.MembershipResDTO;
import com.mvp.membership.entity.Membership;

import java.util.ArrayList;
import java.util.List;

public class MembershipConverter {

    public static MembershipDTO entityToDto(Membership membership) {
        return MembershipDTO.builder()
                .licensePlate(membership.getLicensePlate())
                .startDate(membership.getStartDate())
                .endDate(membership.getEndDate())
                .phoneNumber(membership.getPhoneNumber())
                .name(membership.getName())
                .build();
    }

    public static Membership dtoToEntity(MembershipDTO membershipDTO) {
        return Membership.builder()
                .licensePlate(membershipDTO.getLicensePlate())
                .startDate(membershipDTO.getStartDate())
                .endDate(membershipDTO.getEndDate())
                .phoneNumber(membershipDTO.getPhoneNumber())
                .name(membershipDTO.getName())
                .build();
    }

    public static List<MembershipDTO> entityToDtoList(List<Membership> membershipList) {
        return membershipList.stream()
                .map(MembershipConverter::entityToDto)
                .toList();
    }

    public static MembershipResDTO dtoToResDTO(MembershipDTO membershipDTO) {
        return MembershipResDTO
                .builder()
                .licensePlate(membershipDTO.getLicensePlate())
                .startDate(membershipDTO.getStartDate().toLocalDate())
                .endDate(membershipDTO.getEndDate().toLocalDate())
                .phoneNumber(membershipDTO.getPhoneNumber())
                .name(membershipDTO.getName())
                .build();
    }

    public static List<MembershipResDTO> dtoListToResDTOList(List<MembershipDTO> membershipDTOList) {
        List<MembershipResDTO> membershipResDTOList = new ArrayList<>();
        for (MembershipDTO membershipDTO : membershipDTOList) {
            membershipResDTOList.add(dtoToResDTO(membershipDTO));
        }
        return membershipResDTOList;
    }
}
