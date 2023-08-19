package com.rmanage.rmanage.sms.service;

import com.rmanage.rmanage.UserRepository;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.sms.dto.SMSResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class SMSService {
    @Value("${sms.key}")
    private String api_key;// 발급받은 api_key
    @Value("${sms.secret.key}")
    private String api_secret; //

    @Autowired
    final UserRepository userRepository;

    DefaultMessageService messageService;

    // 전화번호 인증 요청
    @Transactional
    public SMSResponseDto sendSMSById(String phonenumber, long userId) {
        try{
            if(phonenumber == null || !Pattern.matches("\\d{11}$", phonenumber)) {
                return new SMSResponseDto(false, 3056, "전화번호값 01012345678 형식으로 전달 필요", null);
            }

            Optional<User> entity = userRepository.findById(userId);
            if(entity.isEmpty()){
                return new SMSResponseDto(false, 3002, "해당하는 근로자 정보가 없음", null);
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
            // coolsms
            // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
            this.messageService = NurigoApp.INSTANCE.initialize(api_key, api_secret, "https://api.coolsms.co.kr");

            Message message = new Message();
            // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
            message.setFrom("01088492305");
            message.setTo(phonenumber);
            message.setText("알매니지 인증번호 : " + String.valueOf(randomNumber));

            SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
            return new SMSResponseDto(true, 1047, "전화번호 인증번호 요청 성공", response);
        } catch(Exception e) {
            System.out.println(e);
            return new SMSResponseDto(false, 3056, "전화번호 인증번호 요청 실패", null);
        }
    }

    // 전화번호 인증번호 인증
    @Transactional
    public SMSResponseDto checkSMSById(String phonenumber, int number, long userId) {
        try{
            if(phonenumber == null || phonenumber == "") {
                return new SMSResponseDto(false, 3057, "전화번호값 전달 안됨", null);
            }
            Optional<User> entity = userRepository.findById(userId);
            if(entity.isEmpty()){
                return new SMSResponseDto(false, 3002, "해당하는 근로자 정보가 없음", null);
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
                return new SMSResponseDto(true, 1048, "인증번호 인증 성공", null);
            } else {
                return new SMSResponseDto(false, 3057, "인증번호가 틀림", null);
            }

        } catch(Exception e) {
            System.out.println(e);
            return new SMSResponseDto(false, 3057, "인증번호 인증 실패", null);
        }
    }
}
