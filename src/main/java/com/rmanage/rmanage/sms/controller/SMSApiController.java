package com.rmanage.rmanage.sms.controller;

import com.rmanage.rmanage.sms.dto.SMSCheckRequestDto;
import com.rmanage.rmanage.sms.dto.SMSRequestDto;
import com.rmanage.rmanage.sms.dto.SMSResponseDto;
import com.rmanage.rmanage.sms.service.SMSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SMSApiController {
    private final SMSService smsService;

    @PostMapping("every/phone/send")
    public ResponseEntity<SMSResponseDto> sendSMS(@RequestBody SMSRequestDto smsRequestDto) {
        SMSResponseDto smsResponseDto = smsService.sendSMSById(smsRequestDto.getPhonenumber(), smsRequestDto.getUserId());

        return ResponseEntity.ok().body(smsResponseDto);
    }

    @PostMapping("every/phone/check")
    public ResponseEntity<SMSResponseDto> checkSMS(@RequestBody SMSCheckRequestDto smsCheckRequestDto) {
        SMSResponseDto smsResponseDto = smsService.checkSMSById(smsCheckRequestDto.getPhonenumber(), smsCheckRequestDto.getNumber(), smsCheckRequestDto.getUserId());

        return ResponseEntity.ok().body(smsResponseDto);
    }
}
