package com.rmanage.rmanage.sms.service;

import com.rmanage.rmanage.UserRepository;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.sms.dto.SMSResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class SMSService {

    @Autowired
    final UserRepository userRepository;

    // 전화번호번호 인증 요청
    @Transactional
    public SMSResponseDto sendSMSById(String phonenumber, long userId) {
        try{
            if(phonenumber == null) {
                return new SMSResponseDto(false, 3013, "전화번호값 전달 안됨", null);
            }

            Optional<User> entity = userRepository.findById(userId);
            if(entity.isEmpty()){
                return new SMSResponseDto(false, 3013, "해당하는 근로자 정보가 없음", null);
            }
            User user = entity.get();
            // 난수 생성해서 저장
            Random random = new Random();
            random.setSeed(System.currentTimeMillis()); // 정말 랜덤하게 나오도록!
            int randomNumber = (int)(random.nextInt(899999)) + 100000;  //  (100000 ~ 999999)
            user.phoneAuthCodeUpdate(randomNumber);
            // 문자 보내기(임시로 결과값에 담음)
            System.out.println(user.getPhoneAuthCode());
            List<String> list = new ArrayList<>();
            list.add(String.valueOf(randomNumber));
            return new SMSResponseDto(true, 1011, "전화번호 인증번호 요청 성공", list);
        } catch(Exception e) {
            System.out.println(e);
            return new SMSResponseDto(false, 3013, "전화번호 인증번호 요청 실패", null);
        }
    }

    // 전화번호 인증번호 인증
    @Transactional
    public SMSResponseDto checkSMSById(String phonenumber, int number, long userId) {
        try{
            if(phonenumber == null) {
                return new SMSResponseDto(false, 1048, "전화번호값 전달 안됨", null);
            }
            Optional<User> entity = userRepository.findById(userId);
            if(entity.isEmpty()){
                return new SMSResponseDto(false, 3013, "해당하는 근로자 정보가 없음", null);
            }
            User user = entity.get();
            if(user.getPhoneAuthCode() == 0) {
                return new SMSResponseDto(false, 3057, "인증번호를 다시 재발급해주세요.", null);
            }
            // number가 맞으면 phonenumber 저장하기!
            if(number == user.getPhoneAuthCode()) {
                // 전화번호 저장 후 성공 처리
                user.phoneUpdate(phonenumber);
                user.phoneAuthCodeUpdate(0); // 초기화
                return new SMSResponseDto(true, 1011, "인증번호 인증 성공", null);
            } else {
                return new SMSResponseDto(false, 3057, "인증번호가 틀림", null);
            }

        } catch(Exception e) {
            System.out.println(e);
            return new SMSResponseDto(false, 3057, "인증번호 인증 실패", null);
        }
    }
}
