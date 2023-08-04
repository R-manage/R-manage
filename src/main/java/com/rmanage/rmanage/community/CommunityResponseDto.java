package com.rmanage.rmanage.community;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class CommunityResponseDto {
    private boolean isSuccess;
    private int code;
    private String message;
    private List<CommunityDto> result;
}
