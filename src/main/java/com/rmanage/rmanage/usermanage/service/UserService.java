package com.rmanage.rmanage.usermanage.service;

import com.rmanage.rmanage.UserRepository;
import com.rmanage.rmanage.document.dto.ResponseDto;
import com.rmanage.rmanage.document.dto.ResultDto;
import com.rmanage.rmanage.entity.BaseTimeEntity;
import com.rmanage.rmanage.entity.Document;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.Worker;
import com.rmanage.rmanage.setting.dto.PwResponseDto;
import com.rmanage.rmanage.usermanage.MailServiceImpl;
import com.rmanage.rmanage.usermanage.dto.UpdatePasswordDto;

import com.rmanage.rmanage.usermanage.dto.UserManageResponseDto;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;


@Service
public class UserService {
    private UserRepository userRepository;
    private MailServiceImpl mailService;

    @Autowired
    public UserService (UserRepository userRepository, MailServiceImpl mailService){
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    //닉네임 중복 확인
    public UserManageResponseDto checkNicknameDuplication(String nickname) {
        try{
            if(userRepository.existsByNickname((nickname))){
                return new UserManageResponseDto(false, 3012,"이미 존재하는 닉네임");
            }
            else{
                return new UserManageResponseDto(true, 1002, "닉네임 사용가능");
            }
        } catch (Exception e){
            throw new RuntimeException("이외 오류", e);
        }
    }


    //비밀번호 찾기 및 임시발급
    public UserManageResponseDto updatePassword(UpdatePasswordDto updatePasswordDto) {
        try {
            User user = userRepository.findUserByEmail(updatePasswordDto.getEmail());
            if (user == null) {
                return new UserManageResponseDto(false, 3017, "존재하지 않는 이메일");
            }
            if (user.getEmail().equals(updatePasswordDto.getEmail())) {
                user.pwUpdate(mailService.sendMail("findPw", updatePasswordDto.getEmail()).getAuthCode());
                return new UserManageResponseDto(true, 1004, "비밀번호 찾기 및 임시발급 성공");
            }
        }   catch (Exception e){
            System.out.println(e);
            return new UserManageResponseDto(false,3010,"비밀번호 찾기 및 임시발급 실패");
        }
        return null;
    }

//    public void join(JoinDto joinRequestDto) throws Exception{
//        if (userRepository.findUserByEmail(joinRequestDto.getEmail()).isPresent()){
//            throw new Exception("이미 존재하는 이메일입니다.");
//        }
//        if (userRepository.findUserByNickname(joinRequestDto.getNickname()).isPresent()){
//            throw new Exception("이미 존재하는 닉네임입니다.");
//        }
}
