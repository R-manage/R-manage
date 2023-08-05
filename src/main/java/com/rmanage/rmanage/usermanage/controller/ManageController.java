package com.rmanage.rmanage.usermanage.controller;

import com.rmanage.rmanage.usermanage.service.JoinService;
import com.rmanage.rmanage.usermanage.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ManageController {
    private final JoinService joinService;
    private final LoginService loginService;

    /*회원가입
    @PostMapping("/every/users/join")
    public String join(@RequestBody JoinDto joinRequestDto) throws Exception{
        joinService.join(joinRequestDto);
        return "/every/users/main";
    }
     */

    //로그인
    @PostMapping("/every/users/login")
    public String login(@RequestParam String email, @RequestParam String password) throws Exception {
        loginService.login(email, password);
        return "/every/users/main";
    }
}
