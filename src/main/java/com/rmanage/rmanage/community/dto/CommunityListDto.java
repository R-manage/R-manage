package com.rmanage.rmanage.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor

public class CommunityListDto {
    private LocalDate createdAt;
    private String writer;
    private String title;
    private int commentCount;

}
