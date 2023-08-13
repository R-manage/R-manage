package com.rmanage.rmanage.sms.service;

import com.rmanage.rmanage.UserRepository;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.sms.dto.SMSResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SMSService {

    @Autowired
    final UserRepository userRepository;

    // 전화번호번호 인증 요청
    public SMSResponseDto sendSMSById(String phonenumber, long userId) {
        try{
            Optional<User> entity = userRepository.findById(userId);
            if(entity.isEmpty()){
                return new SMSResponseDto(false, 3013, "해당하는 근로자 정보가 없음", null);
            }
            User user = entity.get();
            // 난수 생성해서 저장
    
            // 문자 보내기
            return new SMSResponseDto(true, 1011, "전화번호 인증번호 요청 성공", null);
        } catch(Exception e) {
            System.out.println(e);
            return new SMSResponseDto(false, 3013, "전화번호 인증번호 요청 실패", null);
        }
    }

    // 전화번호 인증번호 인증
    public SMSResponseDto checkSMSById(String phonenumber, int number, long userId) {
        try{
            if(phonenumber == null) {
                return new SMSResponseDto(false, 3013, "전화번호값 전달 안됨", null);
            }
            Optional<User> entity = userRepository.findById(userId);
            if(entity.isEmpty()){
                return new SMSResponseDto(false, 3013, "해당하는 근로자 정보가 없음", null);
            }
            User user = entity.get();
            // number가 맞으면 phonenumber 저장하기!
//            if(number == user.getPhoneAuthCode()) {
//                // 전화번호 저장 후 성공 처리
//            } else {
//                return new SMSResponseDto(false, 1011, "인증번호가 틀림", null);
//            }

            return new SMSResponseDto(true, 1011, "전화번호 인증번호 확인 성공", null);
        } catch(Exception e) {
            System.out.println(e);
            return new SMSResponseDto(false, 3013, "전화번호 인증번호 확인 실패", null);
        }
    }
}
