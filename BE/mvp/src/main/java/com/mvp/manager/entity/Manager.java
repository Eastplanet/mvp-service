package com.mvp.manager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;

    public void update(Manager m) {
        this.name = m.getName();
        this.password = m.getPassword();
        this.email = m.getEmail();
        this.phoneNumber = m.getPhoneNumber();
    }
}


