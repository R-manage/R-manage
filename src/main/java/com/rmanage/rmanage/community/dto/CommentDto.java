package com.rmanage.rmanage.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentDto {
    private Long commentId;
    private Long postId;
    private Long userId;
    private String content;
    private boolean isAnonymous;

}
