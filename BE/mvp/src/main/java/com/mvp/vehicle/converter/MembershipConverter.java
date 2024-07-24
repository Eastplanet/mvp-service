package com.mvp.vehicle.converter;

import com.mvp.vehicle.dto.MembershipDTO;
import com.mvp.vehicle.entity.Membership;

public class MembershipConverter {

    public MembershipDTO entityToDto(Membership membership) {
        return MembershipDTO.builder()
                .licencePlate(membership.getLicencePlate())
                .endDate(membership.getEndDate())
                .phoneNumber(membership.getPhoneNumber())
                .name(membership.getName())
                .build();
    }

    public Membership dtoToEntity(MembershipDTO membershipDTO) {
        return Membership.builder()
                .licencePlate(membershipDTO.getLicencePlate())
                .endDate(membershipDTO.getEndDate())
                .phoneNumber(membershipDTO.getPhoneNumber())
                .name(membershipDTO.getName())
                .build();
    }
}
