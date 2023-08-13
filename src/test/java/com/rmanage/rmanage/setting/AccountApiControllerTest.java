package com.rmanage.rmanage.setting;

import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.WorkPlace;
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

    @DisplayName("근무지 데이터 추가 테스트")
    @Test
    public void saveWorkPlace() throws Exception  {
        final WorkPlace workPlace = WorkPlace.builder().name("근무지1").build();
        final WorkPlace workPlace1 = WorkPlace.builder().name("근무지2").build();
    }

    @DisplayName("난수 생성 연습")
    @Test
    public void createRandom() {
        //6자리 난수
        int num = (int)(Math.random() * 899999) + 100000;  //  (100000 ~ 999999)
        System.out.println(num);
    }

}
