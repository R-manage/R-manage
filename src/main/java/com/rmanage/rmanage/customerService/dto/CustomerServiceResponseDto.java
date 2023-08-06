package com.rmanage.rmanage.customerService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rmanage.rmanage.setting.dto.AlertResultDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Data
public class CustomerServiceResponseDto {
    @JsonProperty("isSuccess")
    private boolean isSuccess;

    private int code;

    private String message;

    private List<String> result;
}
