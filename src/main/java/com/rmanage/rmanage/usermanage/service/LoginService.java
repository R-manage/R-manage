package com.rmanage.rmanage.usermanage.service;

import com.rmanage.rmanage.entity.Token;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.usermanage.dto.LoginResponseDto;
import com.rmanage.rmanage.usermanage.repository.LoginRepository;
import com.rmanage.rmanage.usermanage.repository.TokenRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class LoginService {
    private LoginRepository loginRepository;
    private TokenRepository tokenRepository;


    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LoginResponseDto login(String email, String password) {
        try {
            Optional<User> user = loginRepository.findByEmail(email);
            Optional<User> user1 = loginRepository.findByPassword(password);
            if (user == user1) {
                tokenRepository.save(new Token(user.get(), "asdf", "lkjh"));
                return new LoginResponseDto(true, 1003, "로그인 성공");
            }
        } catch (Exception e) {
            System.out.println(e);
            return new LoginResponseDto(false, 3002, "로그인 실패");
        }
        return null;
    }
}
