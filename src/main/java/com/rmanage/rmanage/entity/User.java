package com.rmanage.rmanage.entity;

//  import com.rmanage.rmanage.worker.Worker;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Entity
@Getter
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    private String phoneAuthDate;

    private int phoneAuthCode;

    private LocalDateTime passwordAuthDate;

    @Builder
    public User(String role, String password, String nickname, String phoneNumber, String email, boolean isEmployee, int phoneCode, String phoneAuthDate) {
        this.role = role;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isEmployee = isEmployee;
        this.phoneCode = phoneCode;
        this.phoneAuthDate = phoneAuthDate;
        this.passwordAuthDate = LocalDateTime.now();
        this.phoneAuthCode = 0;
    }

    public void pwUpdate(String password) {
        this.password = password;
    }

    public void phoneAuthCodeUpdate(int phoneAuthCode) { this.phoneAuthCode = phoneAuthCode;}

    public void phoneUpdate(String phonenumber) {
        this.phoneNumber = phonenumber;
        this.phoneAuthDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
