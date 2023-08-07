package com.rmanage.rmanage.document.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResponseDto {
    private boolean isSuccess;
    private int code;
    private String message;
    private List<ResultDto> result;
}
