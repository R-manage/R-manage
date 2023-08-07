package com.rmanage.rmanage.setting.controller;

import com.rmanage.rmanage.setting.dto.*;
import com.rmanage.rmanage.setting.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountApiController {
    private final AccountService accountService;


    @PostMapping("/every/email")
    public ResponseEntity<EmailResponseDto> findEmail(@RequestBody SearchUserId searchUserId) {


        EmailResponseDto findUser = accountService.findEmailById(searchUserId.getUserId());

        return ResponseEntity.ok().body(findUser);
    }

    @PostMapping("/every/phone")
    public ResponseEntity<PhoneResponseDto> findPhone(@RequestBody SearchUserId searchUserId) {


        PhoneResponseDto findUser = accountService.findPhoneById(searchUserId.getUserId());

        return ResponseEntity.ok().body(findUser);
    }

    @PostMapping("/every/pwcheck")
    public ResponseEntity<PwResponseDto> findPhone(@RequestBody SearchPw searchpw) {


        PwResponseDto findUser = accountService.findPwById(searchpw.getUserId(), searchpw.getEmail(), searchpw.getPassword());

        return ResponseEntity.ok().body(findUser);
    }

}
