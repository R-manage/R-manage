package com.rmanage.rmanage.setting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Data
public class SecurityResponseDto {
    @JsonProperty("isSuccess")
    private boolean isSuccess;

    private int code;

    private String message;

    private List<SecurityResultDto> result;
}
