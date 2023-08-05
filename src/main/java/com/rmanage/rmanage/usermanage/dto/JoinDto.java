package com.rmanage.rmanage.usermanage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDto {
    private Long userId;
    private String email;
    private String password;
    private String nickname;

}
