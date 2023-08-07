package com.rmanage.rmanage.customerService.dto;

import com.rmanage.rmanage.entity.CustomerService;
import com.rmanage.rmanage.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomerServiceRequestDto {
    private long userId;
    private String email;
    private String content;

    @Builder
    public CustomerServiceRequestDto(long userId, String email, String content) {
        this.userId = userId;
        this.email = email;
        this.content = content;
    }

    @Builder
    public CustomerService CustomerServicetoEntity(User user) {
        return CustomerService.builder()
                .user(user)
                .email(email)
                .content(content)
                .build();
    }
}

