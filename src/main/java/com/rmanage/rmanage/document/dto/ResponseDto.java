package com.rmanage.rmanage.document.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResponseDto {

    @JsonProperty("isSuccess")
    private Boolean isSuccess;

    private int code;
    private String message;
    private List<ResultDto> result;
}
