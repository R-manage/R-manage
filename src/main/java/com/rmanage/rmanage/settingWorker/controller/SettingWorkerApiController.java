package com.rmanage.rmanage.settingWorker.controller;

import com.rmanage.rmanage.setting.dto.SearchUserId;
import com.rmanage.rmanage.settingWorker.dto.SettingWorkerResponseDto;
import com.rmanage.rmanage.settingWorker.service.SettingWorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class SettingWorkerApiController {

    private final SettingWorkerService settingWorkerService;

    // 근무지 목록 조회
    @PostMapping("/worker/workplace")
    public ResponseEntity<SettingWorkerResponseDto> findAlert(@RequestBody SearchUserId searchUserId) {

        SettingWorkerResponseDto findUser = settingWorkerService.findWorkPlaceById(searchUserId.getUserId());

        return ResponseEntity.ok().body(findUser);
    }

    // 근무지 삭제
    @DeleteMapping("/worker/workplace/{workerId}")
    public ResponseEntity<SettingWorkerResponseDto> deleteAlert(@PathVariable(name = "workerId") long workerId) {

        SettingWorkerResponseDto findUser = settingWorkerService.deleteWorkPlaceById(workerId);

        return ResponseEntity.ok().body(findUser);
    }
}
