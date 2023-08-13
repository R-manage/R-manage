package com.rmanage.rmanage.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SMSRequestDto {
    private String phonenumber;
    private long userId;
}
