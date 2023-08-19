package com.rmanage.rmanage.usermanage;


import com.rmanage.rmanage.usermanage.dto.MailResponseDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MailServiceImpl implements MailService{

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public String makeCode(int size) {
        Random ran = new Random();
        StringBuffer sb = new StringBuffer();
        int num = 0;
        do {
            num = ran.nextInt(75) + 48;
            if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                sb.append((char) num);
            } else {
                continue;
            }
        } while (sb.length() < size);
        return sb.toString();
    }

    @Override
    public String makeHtml(String type, String code) {
        String html = null;
        switch(type) {
            case "join":
                html = "생략";
                break;
            case "findPw":
                html = "생략";
                break;
        }
        return html;
    }

    @Override
    public MailResponseDto sendMail(String type, String email) {
        //타입에 따라
        //1. 인증코드 만들기
        //2. html string만들기
        String authCode = null, html = null, subject = null;
        switch(type) {
            case "join":
                authCode = makeCode(6);
                html = makeHtml(type, authCode);
                subject = "회원가입 이메일 인증코드 입니다";
                break;
            case "findPw":
                authCode = makeCode(10);
                html = makeHtml(type, authCode);
                subject = "비밀번호 재설정 인증코드 입니다";
                break;
        }

        //공통 - 메일보내기
        MimeMessage mail = mailSender.createMimeMessage();
        try {
            mail.setSubject(subject,"utf-8");
            mail.setText(html,"utf-8","html");
            mail.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            mailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
            return new MailResponseDto(false, 1234, "메일 전송 실패", null);
        }

        return new MailResponseDto(true, 1234, "메일 전송 성공", authCode);
    }
}
