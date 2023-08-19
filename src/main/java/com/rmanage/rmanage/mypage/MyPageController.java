package com.rmanage.rmanage.mypage;

import com.rmanage.rmanage.mypage.dto.AdminMyPageResponseDto;
import com.rmanage.rmanage.mypage.dto.WorkerMyPageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyPageController {

    //근로자 불러오는 데이터 : 사진, 닉네임, 근무지, 총 알바시간, 누적금액, 이번달 알바시간, 이번달 총 급여금액
    //사장: 사진, 닉네임, 근로자인원수, 근로자 지급금액, 이번달 총 지급금액


    private final MyPageService myPageService;

    //근로자 마이페이지
    @GetMapping("/worker/mypage")
    public ResponseEntity<WorkerMyPageResponseDto> getWorkerMyPage(
            @PathVariable Long userId,
            @PathVariable Long workerId){
        WorkerMyPageResponseDto workerMyPageResponseDto = myPageService.getWorkerMyPage(userId, workerId);

        return ResponseEntity.ok(workerMyPageResponseDto);

    }

    //사장 마이페이지
    @GetMapping("/admin/mypage")
    public ResponseEntity<AdminMyPageResponseDto> getAdminMyPage(
            @PathVariable Long userId,
            @PathVariable boolean isEmployee){
        AdminMyPageResponseDto adminMyPageResponseDto = myPageService.getAdminMyPage(userId, isEmployee);

        return ResponseEntity.ok(adminMyPageResponseDto);

    }

}



