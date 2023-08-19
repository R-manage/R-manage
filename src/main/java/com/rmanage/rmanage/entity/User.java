package com.rmanage.rmanage.entity;

//  import com.rmanage.rmanage.worker.Worker;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
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
    private String adminCode;
    private int phoneCode;

    private String phoneAuthDate;

    private String phoneAuthCode;

    @Builder
    public User(String role, String password, String nickname, String phoneNumber, String email, boolean isEmployee, String adminCode, int phoneCode, String phoneAuthDate) {
        this.role = role;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isEmployee = isEmployee;
        if (this.isEmployee){
            this.adminCode = null;
        }
        else
            this.adminCode = adminCode;
        this.phoneCode = phoneCode;
        this.phoneAuthDate = phoneAuthDate;
        this.phoneAuthCode = null;
    }
    public List<String> getRoleList(){
        if(this.role.length() > 0){
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }
    //USER ADMIN

    public void pwUpdate(String password) {
        this.password = password;
    }

}
