package com.rmanage.rmanage.mypage;

import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.mypage.dto.AdminMyPageResponseDto;
import com.rmanage.rmanage.mypage.dto.WorkerMyPageResponseDto;
import com.rmanage.rmanage.usermanage.dto.UserManageResponseDto;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyPageController {

    //근로자 불러오는 데이터 : 사진, 닉네임, 근무지, 총 알바시간, 누적금액, 이번달 알바시간, 이번달 총 급여금액
    //사장: 사진, 닉네임, 근로자인원수, 근로자 지급금액, 이번달 총 지급금액


    private final MyPageService myPageService;
    private MyPageRepository myPageRepository;

    //근로자 마이페이지 조회
    @GetMapping("/worker/{workerId}/mypage")
    public ResponseEntity<WorkerMyPageResponseDto> getWorkerMyPage(
            @PathVariable Long workerId){
        WorkerMyPageResponseDto workerMyPageResponseDto = myPageService.getWorkerMyPage(workerId);

        return ResponseEntity.ok(workerMyPageResponseDto);

    }

    //사장 마이페이지 조회
    @GetMapping("/admin/{userId}/mypage")
    public ResponseEntity<AdminMyPageResponseDto> getAdminMyPage(
            @PathVariable Long userId){
        AdminMyPageResponseDto adminMyPageResponseDto = myPageService.getAdminMyPage(userId);

        return ResponseEntity.ok(adminMyPageResponseDto);

    }

    //프로필 이미지 수정
    @PatchMapping("/every/{userId}/profile")
    public ResponseEntity<UserManageResponseDto> updateProfile(
            @PathVariable Long userId, @RequestBody String image){
        UserManageResponseDto userManageResponseDto = myPageService.updateProfile(userId, image);

        return ResponseEntity.ok(userManageResponseDto);
    }


}



