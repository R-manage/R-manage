package com.rmanage.rmanage.usermanage;

import com.rmanage.rmanage.usermanage.dto.MailResponseDto;

public interface MailService {
    String makeCode(int size);
    String makeHtml(String type, String code);
    MailResponseDto sendMail(String type, String email);

}
