package com.rmanage.rmanage.setting.Service;

import com.rmanage.rmanage.SettingRepository;
import com.rmanage.rmanage.UserRepository;
import com.rmanage.rmanage.entity.Setting;
import com.rmanage.rmanage.setting.dto.AlertResponseDto;
import com.rmanage.rmanage.setting.dto.AlertResultDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AlertService {

    @Autowired
    final UserRepository userRepository;
    @Autowired
    final SettingRepository settingRepository;

//    public void createSave() { // 테스트를 위한 더미데이터 넣는 함수
//        long userId = 6;
//        boolean alarm = false;
//        boolean preview = false;
//        boolean pushAlarm = false;
//        boolean pushSound = false;
//        boolean pushVibration = false;
//        String accountSecurity = "a";
//
//        Optional<User> user = userRepository.findById(userId);
//        User entity = user.get();
//        settingRepository.save(Setting.builder().alarm(alarm).preview(preview).pushAlarm(pushAlarm).pushSound(pushSound).pushVibration(pushVibration).accountSecurity(accountSecurity).user(entity).build());
//
//        long userId1 = 7;
//        boolean alarm1 = true;
//        boolean preview1 = true;
//        boolean pushAlarm1 = true;
//        boolean pushSound1 = true;
//        boolean pushVibration1 = true;
//        String accountSecurity1 = "b";
//
//        Optional<User> user1 = userRepository.findById(userId1);
//        User entity1 = user1.get();
//        settingRepository.save(Setting.builder().alarm(alarm1).preview(preview1).pushAlarm(pushAlarm1).pushSound(pushSound1).pushVibration(pushVibration1).accountSecurity(accountSecurity1).user(entity1).build());
//    }

    // 알림 설정 조회
    public AlertResponseDto findAlertById(long userId) {
        // createSave();
        try {
            Optional<Setting> entity = settingRepository.findById(userId);
            if(entity.isEmpty()){
                return new AlertResponseDto(false,3002,"존재하지 않는 회원",null);
            }
            Setting setting = entity.get();
            // 조회 성공
            List<AlertResultDto> alertResult = new ArrayList<>();

            alertResult.add(new AlertResultDto(setting.isAlarm(), setting.isPreview(), setting.isPushAlarm(), setting.isPushSound(), setting.isPushVibration()));

            return new AlertResponseDto(true,1052,"알람설정 조회 성공", alertResult);
        }   catch (Exception e){
            System.out.println(e);
            return new AlertResponseDto(false,3061,"알람설정 조회 실패",null);
        }
    }

    // 알림 설정 수정
    @Transactional
    public AlertResponseDto updateAlertById(long userId, boolean alarm, boolean preview, boolean pushAlarm, boolean pushSound, boolean pushVibration) {
        try {
            Optional<Setting> entity = settingRepository.findById(userId);
            if(entity.isEmpty()){
                return new AlertResponseDto(false,3002,"존재하지 않는 회원",null);
            }
            Setting setting = entity.get();
            // 조회 성공
            setting.update(alarm, preview, pushAlarm, pushSound, pushVibration);
            List<AlertResultDto> alertResult = new ArrayList<>();

            alertResult.add(new AlertResultDto(setting.isAlarm(), setting.isPreview(), setting.isPushAlarm(), setting.isPushSound(), setting.isPushVibration()));

            return new AlertResponseDto(true,1053,"알람설정 수정 성공", alertResult);
        }   catch (Exception e){
            System.out.println(e);
            return new AlertResponseDto(false,3062,"알람설정 수정 실패",null);
        }
    }
}
