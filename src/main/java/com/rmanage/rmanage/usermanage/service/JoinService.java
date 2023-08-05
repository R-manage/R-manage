package com.rmanage.rmanage.usermanage.service;

import com.rmanage.rmanage.entity.Token;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.usermanage.dto.JoinDto;
import com.rmanage.rmanage.usermanage.repository.JoinRepository;
import com.rmanage.rmanage.usermanage.repository.LoginRepository;
import com.rmanage.rmanage.usermanage.repository.TokenRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class JoinService {
    private JoinDto joinDto;
    private JoinRepository joinRepository;

    @Autowired
    public JoinService(JoinRepository joinRepository){
        this.joinRepository = joinRepository;
    }
    public void join(JoinDto joinRequestDto) throws Exception{
        if (joinRepository.findByEmail(joinRequestDto.getEmail()).isPresent()){
            throw new Exception("이미 존재하는 이메일입니다.");
        }
        if (joinRepository.findByNickname(joinRequestDto.getNickname()).isPresent()){
            throw new Exception("이미 존재하는 닉네임입니다.");
        }
        joinRepository.save(new User(joinDto.getEmail(), joinDto.getPassword(), joinDto.getAccessToken()));
    }
}
