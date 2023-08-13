package com.rmanage.rmanage.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SMSCheckRequestDto {
    private String phonenumber;
    private int number;
    private long userId;
}
