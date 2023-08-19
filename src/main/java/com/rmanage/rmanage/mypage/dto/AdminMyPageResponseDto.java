package com.rmanage.rmanage.mypage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor

public class AdminMyPageResponseDto {
    @JsonProperty("isSuccess")
    private boolean isSuccess;
    private int code;
    private String message;
    private List<AdminMyPageDto> result;

}
