package com.mvp.membership.service;

import com.mvp.common.exception.RestApiException;
import com.mvp.common.exception.StatusCode;
import com.mvp.logger.converter.VehicleLogConverter;
import com.mvp.logger.dto.VehicleLogDTO;
import com.mvp.logger.entity.VehicleLog;
import com.mvp.membership.converter.MembershipConverter;
import com.mvp.membership.dto.MembershipDTO;
import com.mvp.membership.entity.Membership;
import com.mvp.membership.repository.MembershipRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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

    @Transactional
    public MembershipDTO updateMembership(MembershipDTO membershipDTO) {

        Membership findByPlate = membershipRepository.findByLicensePlate(membershipDTO.getLicensePlate());
        if(findByPlate == null || findByPlate.getId() == null) {
            throw new RestApiException(StatusCode.BAD_REQUEST);
        }
        findByPlate.update(membershipDTO);
        return MembershipConverter.entityToDto(findByPlate);
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
        List<MembershipDTO> membershipDTOS = MembershipConverter.entityToDtoList(membershipList);
        // Immutable list를 mutable list로 변환
        List<MembershipDTO> mutableMembershipDTOS = new ArrayList<>(membershipDTOS);

        mutableMembershipDTOS.sort((o1, o2) -> {
            if (o1.getEndDate() == null && o2.getEndDate() == null) {
                return 0;
            }
            if (o1.getEndDate() == null) {
                return 1; // endDate가 없는 경우 뒤로 보냄
            }
            if (o2.getEndDate() == null) {
                return -1; // endDate가 없는 경우 뒤로 보냄
            }
            return o2.getEndDate().compareTo(o1.getEndDate()); // 내림차순 정렬
        });
        return mutableMembershipDTOS;
    }

    public List<MembershipDTO> findMembershipDate(LocalDateTime start, LocalDateTime end) {
        end = end.withDayOfMonth(1).plusMonths(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<Membership> allByExitTimeBetween = membershipRepository.findByLicensePlateEntranceTimeBetween(start, end);
        return MembershipConverter.entityToDtoList(allByExitTimeBetween);
    }

    public boolean isOwnMemberships(String licensePlate) {
        Membership byLicensePlate = membershipRepository.findByLicensePlate(licensePlate);
        if(byLicensePlate == null) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(byLicensePlate.getStartDate()) && now.isBefore(byLicensePlate.getEndDate());
    }
}
