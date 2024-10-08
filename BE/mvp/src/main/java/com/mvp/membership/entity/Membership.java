package com.mvp.membership.entity;

import com.mvp.membership.converter.MembershipConverter;
import com.mvp.membership.dto.MembershipDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "memberships")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate", length = 255, nullable = false)
    private String licensePlate;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "phone_number", length = 255, nullable = false)
    private String phoneNumber;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    public void update(MembershipDTO membershipDTO){
        this.name = membershipDTO.getName();
        this.endDate = membershipDTO.getEndDate();
        this.licensePlate = membershipDTO.getLicensePlate();
        this.phoneNumber = membershipDTO.getPhoneNumber();
    }
}
