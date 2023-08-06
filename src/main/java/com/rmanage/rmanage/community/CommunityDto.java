package com.rmanage.rmanage.community;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rmanage.rmanage.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommunityDto {
    private Long userId;
    private Long postId;
    // private Long commentId;
    private LocalDateTime createdAt;
    private String writer;
    private String title;
    private int commentCount;
    private String content;
    private String type;


}
