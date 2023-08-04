package com.rmanage.rmanage.setting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class EmailResultDto {
    private String email;
    private LocalDateTime validity;
}
