package com.rmanage.rmanage.community.controller;

import com.rmanage.rmanage.community.dto.*;
import com.rmanage.rmanage.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
// @RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    //글 목록 조회
    @GetMapping("/every/{workplaceId}/community/{type}")
    public ResponseEntity<CommunityListResponseDto> getCommunityList(
            @PathVariable Long workplaceId,
            @PathVariable String type){
        CommunityListResponseDto communityListResponseDto = communityService.getCommunityList(workplaceId, type);
        return ResponseEntity.ok(communityListResponseDto);
    }
    // 알바생 잡담 목록 조회
    @GetMapping("/worker/{workplaceId}/community/{type}")
    public ResponseEntity<CommunityListResponseDto> workerGetCommunityList(
            @PathVariable Long workplaceId,
            @PathVariable String type){
        CommunityListResponseDto communityListResponseDto = communityService.getCommunityList(workplaceId, type);
        return ResponseEntity.ok(communityListResponseDto);
    }
    // 글 하나 조회
    @GetMapping("/every/community/{postId}")
    public ResponseEntity<CommunityResponseDto> getCommunity(
            @PathVariable Long postId){
        CommunityResponseDto communityResponseDto = communityService.getCommunity(postId);
        return ResponseEntity.ok(communityResponseDto);
    }
    // 알바생 잡담 글 하나 조회
    @GetMapping("/worker/community/{postId}")
    public ResponseEntity<CommunityResponseDto> workerGetCommunity(
            @PathVariable Long postId){
        CommunityResponseDto communityResponseDto = communityService.getCommunity(postId);
        return ResponseEntity.ok(communityResponseDto);
    }

    // 글쓰기
    @PostMapping("/every/{workplaceId}/community/{type}")
    public ResponseEntity postCommunity(@PathVariable Long workplaceId,
                                        @PathVariable String type,
                                        @RequestBody CommunityDto communityDto, @RequestParam(value = "image", required = false) String image) {
        CommunitySaveResponseDto communitySaveResponseDto = communityService.postCommunity(
                workplaceId, type, communityDto.getUserId(), communityDto.getTitle(), communityDto.getContent(), image);
        return ResponseEntity.ok(communitySaveResponseDto);
    }
    // 공지사항 글쓰기
    @PostMapping("/admin/{workplaceId}/community/{type}")
    public ResponseEntity adminPostCommunity(@PathVariable Long workplaceId,
                                        @PathVariable String type,
                                        @RequestBody CommunityDto communityDto, @RequestParam(value = "image", required = false) String image) {
        CommunitySaveResponseDto communitySaveResponseDto = communityService.postCommunity(
                workplaceId, type, communityDto.getUserId(), communityDto.getTitle(), communityDto.getContent(), image);
        return ResponseEntity.ok(communitySaveResponseDto);
    }
    // 알바생 잡담 글쓰기
    @PostMapping("/worker/{workplaceId}/community/{type}")
    public ResponseEntity workerPostCommunity(@PathVariable Long workplaceId,
                                             @PathVariable String type,
                                             @RequestBody CommunityDto communityDto, @RequestParam(value = "image", required = false) String image) {
        CommunitySaveResponseDto communitySaveResponseDto = communityService.postCommunity(
                workplaceId, type, communityDto.getUserId(), communityDto.getTitle(), communityDto.getContent(), image);
        return ResponseEntity.ok(communitySaveResponseDto);
    }

    // 글 수정
    @PatchMapping("/every/community/{postId}")
    public ResponseEntity patchCommunity(
            @PathVariable Long postId,
            @RequestBody CommunityDto communityDto, @RequestParam(value = "image", required = false) String image){
        CommunityResponseDto communityResponseDto = communityService.patchCommunity(postId, communityDto.getTitle(), communityDto.getContent(), image);
        return ResponseEntity.ok(communityResponseDto);
    }
    // 공지사항 글 수정
    @PatchMapping("/admin/community/{postId}")
    public ResponseEntity adminPatchCommunity(
            @PathVariable Long postId,
            @RequestBody CommunityDto communityDto, @RequestParam(value = "image", required = false) String image){
        CommunityResponseDto communityResponseDto = communityService.patchCommunity(postId, communityDto.getTitle(), communityDto.getContent(), image);
        return ResponseEntity.ok(communityResponseDto);
    }
    // 알바생 잡담 글 수정
    @PatchMapping("/worker/community/{postId}")
    public ResponseEntity workerPatchCommunity(
            @PathVariable Long postId,
            @RequestBody CommunityDto communityDto, @RequestParam(value = "image", required = false) String image){
        CommunityResponseDto communityResponseDto = communityService.patchCommunity(postId, communityDto.getTitle(), communityDto.getContent(), image);
        return ResponseEntity.ok(communityResponseDto);
    }


    // 글 삭제
    @DeleteMapping("/every/community/{postId}")
    public ResponseEntity deleteCommunity(
            @PathVariable Long postId){
        CommunityResponseDto communityResponseDto = communityService.deleteCommunity(postId);
        return ResponseEntity.ok(communityResponseDto);
    }
    // 공지사항 글 삭제
    @DeleteMapping("/admin/community/{postId}")
    public ResponseEntity adminDeleteCommunity(
            @PathVariable Long postId){
        CommunityResponseDto communityResponseDto = communityService.deleteCommunity(postId);
        return ResponseEntity.ok(communityResponseDto);
    }
    // 알바생 잡담 글 삭제
    @DeleteMapping("/worker/community/{postId}")
    public ResponseEntity workerDeleteCommunity(
            @PathVariable Long postId){
        CommunityResponseDto communityResponseDto = communityService.deleteCommunity(postId);
        return ResponseEntity.ok(communityResponseDto);
    }
}
