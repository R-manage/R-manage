package com.rmanage.rmanage.settingAdmin.service;

import com.rmanage.rmanage.UserRepository;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.Worker;
import com.rmanage.rmanage.settingAdmin.dto.SettingAdminResponseDto;
import com.rmanage.rmanage.settingAdmin.dto.SettingAdminResultDto;
import com.rmanage.rmanage.workPlace.WorkPlaceRepository;
import com.rmanage.rmanage.worker.WorkerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SettingAdminService {
    @Autowired
    final UserRepository userRepository;

    @Autowired
    final WorkerRepository workerRepository;

    @Autowired
    final WorkPlaceRepository workPlaceRepository;

    // 근로자 정보 조회
    public SettingAdminResponseDto findWorkerById(long userId) {

        try {
            Optional<User> entity = userRepository.findById(userId);
            if(entity.isEmpty()){
                return new SettingAdminResponseDto(false,3002,"존재하지 않는 회원",null);
            }
            User user = entity.get();
            List<Worker> worker = workerRepository.findByUser(user);
            if(worker.isEmpty()){
                return new SettingAdminResponseDto(false,3003,"해당하는 근무지 정보가 없음",null);
            }
            if(worker.size() != 1) {
                // 무조건 1개여야 함!
                return new SettingAdminResponseDto(false,3003,"근무지 정보 오류",null);
            }
            List<Worker> employee = workerRepository.findByWorkPlace(worker.get(0).getWorkPlace());
            // 조회 성공
            List<SettingAdminResultDto> settingAdminResult = new ArrayList<>();

            employee.remove(worker.get(0)); // 사장 본인은 제거하기!
            employee.stream().forEach(data -> settingAdminResult.add(new SettingAdminResultDto(data.getWorkerId(), data.getName(), data.isManager())));

            return new SettingAdminResponseDto(true,1054,"근로자 정보 조회 성공", settingAdminResult);
        }   catch (Exception e){
            System.out.println(e);
            return new SettingAdminResponseDto(false,3063,"근로자 정보 조회 실패",null);
        }
    }

    // 근로자 관리자 권한 수정
    @Transactional
    public SettingAdminResponseDto updateWorkerById(long workerId, boolean isManager) {

        try {
            Optional<Worker> entity = workerRepository.findById(workerId);
            if(entity.isEmpty()){
                return new SettingAdminResponseDto(false,3004,"해당하는 근무근로자 정보가 없음",null);
            }
            Worker worker = entity.get();
            // 조회 성공
            worker.updateManager(isManager);

            return new SettingAdminResponseDto(true,1055,"근로자 관리자 권한 수정 성공", null);
        }   catch (Exception e){
            System.out.println(e);
            return new SettingAdminResponseDto(false,3064,"근로자 관리자 권한 수정 실패",null);
        }
    }
}
