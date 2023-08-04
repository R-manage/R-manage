package com.rmanage.rmanage.setting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.rmanage.rmanage.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Data
public class EmailResponseDto {

    @JsonProperty("isSuccess")
    private boolean isSuccess;

    private int code;

    private String message;

    private List<EmailResultDto> result;

//    public EmailResponseDto(User entity) {
//        this.email = entity.getEmail();
//        this.validity = entity.getCreatedAt();
//    }
}

