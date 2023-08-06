package com.rmanage.rmanage.setting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PwRequestDto {

    private long userId;
    private String password;
}
