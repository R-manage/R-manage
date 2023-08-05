package com.rmanage.rmanage.usermanage.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDto {
    private boolean isSuccess;
    private int code;
    private String message;
}
