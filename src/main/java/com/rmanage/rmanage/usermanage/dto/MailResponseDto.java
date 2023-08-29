package com.rmanage.rmanage.usermanage.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailResponseDto {
    private boolean isSuccess;
    private int code;
    private String message;
    private String authCode;
}
