package com.rmanage.rmanage.usermanage.controller;

import com.rmanage.rmanage.UserRepository;
import com.rmanage.rmanage.document.dto.ResponseDto;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.WorkPlace;
import com.rmanage.rmanage.entity.Worker;
import com.rmanage.rmanage.setting.dto.PwResponseDto;
import com.rmanage.rmanage.usermanage.MailService;
import com.rmanage.rmanage.usermanage.dto.JoinDto;
import com.rmanage.rmanage.usermanage.dto.MailResponseDto;
import com.rmanage.rmanage.usermanage.dto.UpdatePasswordDto;
import com.rmanage.rmanage.usermanage.dto.UserManageResponseDto;
import com.rmanage.rmanage.usermanage.service.UserService;
import com.rmanage.rmanage.workPlace.WorkPlaceRepository;
import com.rmanage.rmanage.worker.WorkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ManageController {
    private final UserService userService;
    private final WorkPlaceRepository workPlaceRepository;
    private final WorkerRepository workerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MailService mailService;

    //회원가입
    @PostMapping("/join")
    public UserManageResponseDto join(@RequestBody JoinDto joinDto){
        try{
            if (userRepository.findUserByEmail(joinDto.getEmail()) != null){
                return new UserManageResponseDto(false, 3011, "이미 존재하는 이메일");
            }
            joinDto.setPassword(bCryptPasswordEncoder.encode(joinDto.getPassword()));
            User user = new User();
            user.setEmail(joinDto.getEmail());
            user.setPassword(joinDto.getPassword());
            user.setNickname(joinDto.getNickname());
            user.setRole(joinDto.getRole());
            User newUser = userRepository.save(user);
            if (user.getRole().equals("ROLE_ADMIN")) {
                String authCode = null;
                while(true){
                    authCode = mailService.makeCode(6);
                    if(workPlaceRepository.findWorkPlaceByAdminCode(authCode) == null){
                        break;
                    } else {
                        System.out.println("인증코드가 겹침!");
                    }
                }
                WorkPlace workPlace = workPlaceRepository.save(new WorkPlace(authCode, authCode));
                workerRepository.save(Worker.builder()
                        .workPlace(workPlace)
                        .user(newUser)
                        .build());
            }
            return new UserManageResponseDto(true, 1001, "회원가입 성공");
        }catch (Exception e){
            System.out.println(e);
        }
        return new UserManageResponseDto(false, 3018, "회원가입 실패");
    }
    //닉네임 중복확인
    //중복이면 중복입니다, 중복 아니면 nickname return
    @GetMapping("/{nickname}/exists")
    public ResponseEntity checkNicknameDuplication(@PathVariable String nickname){
        return ResponseEntity.ok(userService.checkNicknameDuplication(nickname));
    }

    //비밀번호 찾기(임시비밀번호로 변경)
    //password=user.updatePW(password)하면될듯?
    @PatchMapping("/find-password")
    public ResponseEntity patchPassword(@RequestBody UpdatePasswordDto updatePasswordDto){
        UserManageResponseDto userManageResponseDto = userService.updatePassword(updatePasswordDto);
        return ResponseEntity.ok(userManageResponseDto);
    }

    //메일 전송(이메일인증코드, 임시비밀번호)
    @PostMapping("/sendmail")
    public ResponseEntity<Map<String, Object>> sendMail(@RequestBody Map<String, String> map){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        MailResponseDto authCode = mailService.sendMail(map.get("type"), map.get("email"));
        if (authCode.equals("메일 전송 실패")) {
            resultMap.put("message", "FAIL");
            status = HttpStatus.ACCEPTED;
        }else{
            resultMap.put("message", "SUCCESS");
            resultMap.put("authCode", authCode);
            status = HttpStatus.ACCEPTED;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}
