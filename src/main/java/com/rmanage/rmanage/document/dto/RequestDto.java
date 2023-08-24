package com.rmanage.rmanage.document.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private LocalDate expireDate;
    private String image;
    private String type;
}
