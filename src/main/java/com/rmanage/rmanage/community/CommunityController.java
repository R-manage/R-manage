package com.rmanage.rmanage.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
// @RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    @GetMapping("/every/{workplaceId}/community/{type}")
    public ResponseEntity<CommunityResponseDto> getCommunityList(
            @PathVariable Long workplaceId,
            @PathVariable String type){
        CommunityResponseDto communityResponseDto = communityService.getCommunityList(workplaceId, type);

        return ResponseEntity.ok(communityResponseDto);

    }
    @PostMapping("/every/{workplaceId}/community/{type}")
    public ResponseEntity writeCommunity(@PathVariable Long workplaceId,
                                                @PathVariable String type,
                                                @RequestBody CommunityDto communityDto) {
        CommunityResponseDto communityResponseDto = communityService.writeCommunity(
                workplaceId, type, communityDto.getUserId(), communityDto.getTitle(), communityDto.getContent());
        return ResponseEntity.ok(communityResponseDto);
    }

}
