package com.rmanage.rmanage.setting.controller;

import com.rmanage.rmanage.setting.dto.AlertResultDto;
import com.rmanage.rmanage.setting.dto.SearchAlert;
import com.rmanage.rmanage.setting.dto.SearchUserId;
import com.rmanage.rmanage.setting.Service.AlertService;
import com.rmanage.rmanage.setting.dto.AlertResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AlertApiController {

    private final AlertService alertService;

    // 알림설정 조회
    @PostMapping("/every/alert")
    public ResponseEntity<AlertResponseDto> findAlert(@RequestBody SearchUserId searchUserId) {

        AlertResponseDto findUser = alertService.findAlertById(searchUserId.getUserId());

        return ResponseEntity.ok().body(findUser);
    }

    // 알림설정 수정
    @PatchMapping("/every/alert")
    public ResponseEntity<AlertResponseDto> updateAlert(@RequestBody SearchAlert searchAlert) {

        AlertResponseDto findUser = alertService.updateAlertById(searchAlert.getUserId(), searchAlert.isAlarm(), searchAlert.isPreview(), searchAlert.isPushAlarm(), searchAlert.isPushSound(), searchAlert.isPushVibration());

        return ResponseEntity.ok().body(findUser);
    }
}
