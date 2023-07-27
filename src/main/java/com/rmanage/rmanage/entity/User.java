package com.rmanage.rmanage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity

public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String role;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String email;
    private boolean isEmployee;
    private int phoneCode;
    private boolean isPhoneAuth;

    public User(String role, String password, String nickname, String phoneNumber, String email, boolean isEmployee, int phoneCode, boolean isPhoneAuth) {
        this.role = role;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isEmployee = isEmployee;
        this.phoneCode = phoneCode;
        this.isPhoneAuth = isPhoneAuth;
    }
}
