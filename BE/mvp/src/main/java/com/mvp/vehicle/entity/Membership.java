package com.mvp.vehicle.entity;

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

    @Column(name = "licence_plate", length = 255)
    private String licencePlate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "phone_number", length = 255)
    private String phoneNumber;

    @Column(name = "name", length = 255)
    private String name;
}
