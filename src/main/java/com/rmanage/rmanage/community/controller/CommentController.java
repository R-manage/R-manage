package com.rmanage.rmanage.community.controller;

import com.rmanage.rmanage.community.dto.CommentDto;
import com.rmanage.rmanage.community.dto.CommentResponseDto;
import com.rmanage.rmanage.community.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /*
    @GetMapping("/every/community/{postId}/comment")
    public ResponseEntity<CommentResponseDto>getComment(
            @PathVariable Long postId){
        CommentResponseDto commentResponseDto = commentService.getComment(postId);
        return ResponseEntity.ok(commentResponseDto);

    }
     */
    // 댓글 작성
    @PostMapping("/every/community/{postId}/comment")
    public ResponseEntity<CommentResponseDto> postComment(
            @PathVariable Long postId,
            @RequestBody CommentDto commentDto){
        CommentResponseDto commentResponseDto = commentService.postComment(postId, commentDto.getContent(), commentDto.getUserId(), commentDto.isAnonymous());
        return ResponseEntity.ok(commentResponseDto);
    }
    // 알바생 댓글 작성
    @PostMapping("/worker/community/{postId}/comment")
    public ResponseEntity<CommentResponseDto> workerPostComment(
            @PathVariable Long postId,
            @RequestBody CommentDto commentDto){
        CommentResponseDto commentResponseDto = commentService.postComment(postId, commentDto.getContent(), commentDto.getUserId(), commentDto.isAnonymous());
        return ResponseEntity.ok(commentResponseDto);
    }

    // 댓글 삭제
    @DeleteMapping("/every/community/{postId}/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId){
        CommentResponseDto commentResponseDto = commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok(commentResponseDto);
    }
    // 알바생 잡담 댓글 삭제
    @DeleteMapping("/worker/community/{postId}/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> adminPostComment(
            @PathVariable Long postId,
            @PathVariable Long commentId){
        CommentResponseDto commentResponseDto = commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok(commentResponseDto);
    }

}



