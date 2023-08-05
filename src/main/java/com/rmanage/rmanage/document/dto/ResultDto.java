package com.rmanage.rmanage.document.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ResultDto {
    private Long documentId;
    private String type;
    private String imageUrl;
    private LocalDate expireDate;
}
