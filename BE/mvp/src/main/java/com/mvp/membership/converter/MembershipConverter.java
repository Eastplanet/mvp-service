package com.mvp.membership.converter;

import com.mvp.membership.dto.MembershipDTO;
import com.mvp.membership.entity.Membership;

import java.util.List;

public class MembershipConverter {

    public static MembershipDTO entityToDto(Membership membership) {
        return MembershipDTO.builder()
                .licensePlate(membership.getLicensePlate())
                .endDate(membership.getEndDate())
                .phoneNumber(membership.getPhoneNumber())
                .name(membership.getName())
                .build();
    }

    public static Membership dtoToEntity(MembershipDTO membershipDTO) {
        return Membership.builder()
                .licensePlate(membershipDTO.getLicensePlate())
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
}
