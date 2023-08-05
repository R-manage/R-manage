package com.rmanage.rmanage.setting.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SearchPw {

    private long userId;
    private String email;
    private String password;
}
