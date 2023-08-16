package com.rmanage.rmanage.setting.Service;

import com.rmanage.rmanage.UserRepository;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.setting.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {

    final UserRepository userRepository;

//    public void createSave() { // 테스트를 위한 더미데이터 넣는 임시 함수
//        String role = "employee";
//        String password = "a";
//        String nickname = "a";
//        String phoneNumber = "010-0000-0000";
//        String email = "aaa@aaa.aaa";
//        Boolean isEmployee = true;
//        int phoneCode = 1234;
//        String phoneAuthDate = "2022-02-02";
//
//        userRepository.save(User.builder().role(role).password(password).nickname(nickname).phoneNumber(phoneNumber).email(email).isEmployee(isEmployee).phoneCode(phoneCode).phoneAuthDate(phoneAuthDate).build());
//
//        String role1 = "employee1";
//        String password1 = "b";
//        String nickname1 = "b";
//        String phoneNumber1 = "010-0000-0000";
//        String email1 = "bbb@bbb.bbb";
//        Boolean isEmployee1 = true;
//        int phoneCode1 = 1234;
//        String phoneAuthDate1 = "2023-03-03";
//
//        userRepository.save(User.builder().role(role1).password(password1).nickname(nickname1).phoneNumber(phoneNumber1).email(email1).isEmployee(isEmployee1).phoneCode(phoneCode1).phoneAuthDate(phoneAuthDate1).build());
//    }

    // 계정 조회
    public AccountResponseDto findAccountById(long userId) {
        try {
            Optional<User> entity = userRepository.findById(userId);
            if(entity.isEmpty()){
                return new AccountResponseDto(false,3002,"존재하지 않는 회원",null);
            }
            User user = entity.get();
            // 조회 성공
            List<AccountResultDto> accountResult = new ArrayList<>();
            accountResult.add(new AccountResultDto(user.getNickname(), user.getEmail(), user.getPhoneNumber(), null));

            return new AccountResponseDto(true,1044,"계정 조회 성공", accountResult);
        }   catch (Exception e){
            System.out.println(e);
            return new AccountResponseDto(false,3053,"계정 조회 실패",null);
        }
    }

    // 이메일 조회
    public EmailResponseDto findEmailById(long userId) {
        // createSave();
        try {
            Optional<User> entity = userRepository.findById(userId);
            if(entity.isEmpty()){
                return new EmailResponseDto(false,3002,"존재하지 않는 회원",null);
            }
            User user = entity.get();
            // 조회 성공
            List<EmailResultDto> emailResult = new ArrayList<>();
            emailResult.add(new EmailResultDto(user.getEmail(), user.getCreatedAt()));

            return new EmailResponseDto(true,1045,"이메일 조회 성공", emailResult);
        }   catch (Exception e){
            System.out.println(e);
            return new EmailResponseDto(false,3054,"이메일 조회 실패",null);
        }
//        User entity = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("not found: " + userId));
//        return new EmailResponseDto(entity);
    }

    // 전화번호 조회
    public PhoneResponseDto findPhoneById(long userId) {
        try {
            Optional<User> entity = userRepository.findById(userId);
            if(entity.isEmpty()){
                return new PhoneResponseDto(false,3002,"존재하지 않는 회원",null);
            }
            User user = entity.get();
            // 조회 성공
            List<PhoneResultDto> phoneResult = new ArrayList<>();
            if(user.getPhoneNumber() == null) {
                phoneResult.add(new PhoneResultDto(false, null, null));
            } else {
                phoneResult.add(new PhoneResultDto(true, user.getPhoneNumber(), user.getPhoneAuthDate()));
            }

            return new PhoneResponseDto(true,1046,"전화번호 조회 성공", phoneResult);
        }   catch (Exception e){
            System.out.println(e);
            return new PhoneResponseDto(false,3055,"전화번호 조회 실패",null);
        }
    }

    // 현재 비밀번호 확인(비밀번호와 이메일)
    public PwResponseDto findPwById(long userId, String email, String password) {
        try {
            Optional<User> entity = userRepository.findById(userId);
            if(entity.isEmpty()){
                return new PwResponseDto(false,3002,"존재하지 않는 회원",null);
            }
            User user = entity.get();
            // 조회 성공
            if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return new PwResponseDto(true,1050,"비밀번호와 이메일 확인 성공", null);
            } else if(user.getEmail().equals(email)) {
                return new PwResponseDto(false,2045,"비밀번호 불일치", null);
            } else if(user.getPassword().equals(password)) {
                return new PwResponseDto(false, 2046, "이메일 불일치", null);
            }
            return new PwResponseDto(false,2047,"비밀번호와 이메일 모두 불일치", null);
        }   catch (Exception e){
            System.out.println(e);
            return new PwResponseDto(false,3011,"비밀번호 확인 실패",null);
        }
    }

    // 비밀번호 변경
    @Transactional
    public PwResponseDto updatePwById(long userId, String password) {
        try {
            Optional<User> entity = userRepository.findById(userId);
            if(entity.isEmpty()){
                return new PwResponseDto(false,3002,"존재하지 않는 회원",null);
            }
            User user = entity.get();
            // 조회 성공
            if(password == "" || password == null) {
                return new PwResponseDto(false,2043,"비밀번호 누락", null);
            }
            user.pwUpdate(password);

            return new PwResponseDto(true,1051,"비밀번호 변경 성공", null);
        }   catch (Exception e){
            System.out.println(e);
            return new PwResponseDto(false,3060,"비밀번호 변경 실패",null);
        }
    }

    // 계정 보안 진단
    public SecurityResponseDto findSecurityById(long userId) {
        try {
            Optional<User> entity = userRepository.findById(userId);
            if(entity.isEmpty()){
                return new SecurityResponseDto(false,3002,"존재하지 않는 회원",null);
            }
            User user = entity.get();
            // 조회 성공
            List<SecurityResultDto> securityResult = new ArrayList<>();
            // 3개월 이내인지 판단
            LocalDateTime currentDateTime = LocalDateTime.now();
            boolean isBadPeriod = user.getPasswordAuthDate().isAfter(currentDateTime.minusMonths(3));

            if(user.getPhoneNumber() == null && !isBadPeriod) { // 비밀번호 변경 날짜를 update_at으로 봐도 되는가??
                securityResult.add(new SecurityResultDto("위험", user.getPasswordAuthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), false, null));
            } else if(user.getPhoneNumber() == null){
                securityResult.add(new SecurityResultDto("보통", user.getPasswordAuthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), false, null));
            } else if(!isBadPeriod) {
                securityResult.add(new SecurityResultDto("보통", user.getPasswordAuthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), true, user.getPhoneNumber()));
            } else {
                securityResult.add(new SecurityResultDto("안전", user.getPasswordAuthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), true, user.getPhoneNumber()));
            }

            return new SecurityResponseDto(true,1049,"계정 보안 진단 성공", securityResult);
        }   catch (Exception e){
            System.out.println(e);
            return new SecurityResponseDto(false,3058,"계정 보안 진단 실패",null);
        }
    }

}
