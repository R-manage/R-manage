package com.rmanage.rmanage.setting;

import com.rmanage.rmanage.UserRepository;
import com.rmanage.rmanage.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountApiControllerTest {

    @DisplayName("/every/email 테스트")
    @Test
    public void everyEmail() throws Exception {
        final User user = User.builder()
                .role("a")
                .password("a")
                .nickname("a")
                .phoneNumber("010-0000-0000")
                .email("aaa@aaa.aaa")
                .isEmployee(false)
                .phoneCode(1111)
                .phoneAuthDate("2022-02-02")
                .build();

    }

}
