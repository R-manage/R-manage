package com.rmanage.rmanage.entity;

import com.rmanage.rmanage.worker.Worker;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workerId")
    private Worker worker;

    private String role;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String email;
    private boolean isEmployee;
    private int phoneCode;
    private boolean isPhoneAuth;
    private String accessToken;

    @Builder
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
    public User(String email, String password, String accessToken){
        this.email = email;
        this.password = password;
        this.accessToken = accessToken;
    }
}
