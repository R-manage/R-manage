package com.rmanage.rmanage.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CommunitySaveDto {
    private Long userId;
    private Long postId;
    // private Long commentId;
    private LocalDateTime createdAt;
    private String writer;
    private String title;
    private String content;
    private String type;
    private String imageUrl;

}
