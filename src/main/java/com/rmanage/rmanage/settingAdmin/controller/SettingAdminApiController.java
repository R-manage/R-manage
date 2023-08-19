package com.rmanage.rmanage.settingAdmin.controller;


import com.rmanage.rmanage.setting.dto.SearchUserId;
import com.rmanage.rmanage.settingAdmin.dto.SettingAdminRequestDto;
import com.rmanage.rmanage.settingAdmin.dto.SettingAdminResponseDto;
import com.rmanage.rmanage.settingAdmin.service.SettingAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SettingAdminApiController {

    private final SettingAdminService settingAdminService;

    // 근로자 정보 조회
    @PostMapping("/admin/manager/list")
    public ResponseEntity<SettingAdminResponseDto> findWorkers(@RequestBody SearchUserId searchUserId) {

        SettingAdminResponseDto findUser = settingAdminService.findWorkerById(searchUserId.getUserId());

        return ResponseEntity.ok().body(findUser);
    }

    @PatchMapping("/admin/manager")
    public ResponseEntity<SettingAdminResponseDto> updateWorker(@RequestBody SettingAdminRequestDto settingAdminRequestDto) {

        SettingAdminResponseDto findUser = settingAdminService.updateWorkerById(settingAdminRequestDto.getWorkerId(), settingAdminRequestDto.isManager());

        return ResponseEntity.ok().body(findUser);
    }
}
