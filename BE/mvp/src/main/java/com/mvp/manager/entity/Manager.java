package com.mvp.manager.entity;

import com.mvp.manager.dto.ManagerDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "manager")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "phone_number", length = 255, nullable = false)
    private String phoneNumber;

    public void update(ManagerDTO managerDTO){
        this.name = managerDTO.getName();
        this.password = managerDTO.getPassword();
        this.email = managerDTO.getEmail();
        this.phoneNumber = managerDTO.getPhoneNumber();
    }
}
