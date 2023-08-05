package com.rmanage.rmanage.community;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class CommunityResponseDto {
    @JsonProperty("isSuccess")
    private boolean isSuccess;
    private int code;
    private String message;
    private List<CommunityDto> result;
}
