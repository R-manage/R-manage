package com.rmanage.rmanage.setting;

import com.rmanage.rmanage.UserRepository;
import com.rmanage.rmanage.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

//    @AfterEach
//    public void cleanup() {
//        userRepository.deleteAll();
//    }

//    @BeforeEach
    @Test
    public void saveAndReadUser() {
        String role = "employee";
        String password = "a";
        String nickname = "a";
        String phoneNumber = "010-0000-0000";
        String email = "aaa@aaa.aaa";
        Boolean isEmployee = true;
        int phoneCode = 1234;
        String phoneAuthDate = "2022-02-02";

        userRepository.save(User.builder().role(role).password(password).nickname(nickname).phoneNumber(phoneNumber).email(email).isEmployee(isEmployee).phoneCode(phoneCode).phoneAuthDate(phoneAuthDate).build());

        String role1 = "employee1";
        String password1 = "b";
        String nickname1 = "b";
        String phoneNumber1 = "010-0000-0000";
        String email1 = "bbb@bbb.bbb";
        Boolean isEmployee1 = true;
        int phoneCode1 = 1234;
        String phoneAuthDate1 = "2023-03-03";

        userRepository.save(User.builder().role(role1).password(password1).nickname(nickname1).phoneNumber(phoneNumber1).email(email1).isEmployee(isEmployee1).phoneCode(phoneCode1).phoneAuthDate(phoneAuthDate1).build());
//        List<User> userList = userRepository.findAll();
//
//        User user = userList.get(0);
//        System.out.println("aaa");
//        System.out.println(user.getEmail());
//        System.out.println(user.getCreatedAt());
//        System.out.println("bbb");

    }
}
