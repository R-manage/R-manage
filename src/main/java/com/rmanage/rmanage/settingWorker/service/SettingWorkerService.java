package com.rmanage.rmanage.settingWorker.service;

import com.rmanage.rmanage.UserRepository;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.WorkPlace;
import com.rmanage.rmanage.entity.Worker;
import com.rmanage.rmanage.settingWorker.dto.SettingWorkerResponseDto;
import com.rmanage.rmanage.settingWorker.dto.SettingWorkerResultDto;
import com.rmanage.rmanage.workPlace.WorkPlaceRepository;
import com.rmanage.rmanage.worker.WorkerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SettingWorkerService {

    @Autowired
    final UserRepository userRepository;

    @Autowired
    final WorkerRepository workerRepository;

    @Autowired
    final WorkPlaceRepository workPlaceRepository;

    public void createWorkPlace() {
        workPlaceRepository.save(WorkPlace.builder().name("근무지1").build());
        workPlaceRepository.save(WorkPlace.builder().name("근무지2").build());
        workPlaceRepository.save(WorkPlace.builder().name("근무지3").build());
    }

    public void createWorker() {
        Optional<User> user = userRepository.findById(13L);
        User entity = user.get();
        Optional<WorkPlace> workPlace = workPlaceRepository.findById(6L);
        WorkPlace entity2 = workPlace.get();

        workerRepository.save(Worker.builder().workPlace(entity2).user(entity).name("근로자명2").color("색상").isCertified(false).isManager(false).hourlyWage(10000).workAuthDate(LocalDateTime.now()).build());
    }

    // 근무지 조회(설정 페이지)
    public SettingWorkerResponseDto findWorkPlaceById(long userId) {
        // createWorker();

        try {
            Optional<User> entity = userRepository.findById(userId);
            if(entity.isEmpty()){
                return new SettingWorkerResponseDto(false,3002,"존재하지 않는 회원",null);
            }
            User user = entity.get();
            List<Worker> worker = workerRepository.findByUser(user);
            if(worker.isEmpty()){
                return new SettingWorkerResponseDto(false,3003,"해당하는 근무지 정보가 없음",null);
            }

            // 조회 성공
            List<SettingWorkerResultDto> settingWorkerResult = new ArrayList<>();
            System.out.println(worker);

            //settingWorkerResult.add(new SettingWorkerResultDto(worker));
            worker.stream().forEach(data -> settingWorkerResult.add(new SettingWorkerResultDto(data.getWorkerId(), data.getWorkPlace().getName(), (data.getWorkAuthDate() != null) ? data.getWorkAuthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null)));
            return new SettingWorkerResponseDto(true,1057,"근무지 조회 성공", settingWorkerResult);
        }   catch (Exception e){
            System.out.println(e);
            return new SettingWorkerResponseDto(false,3066,"근무지 조회 실패",null);
        }
    }

    // 근무지 삭제(설정 페이지)
    @Transactional
    public SettingWorkerResponseDto deleteWorkPlaceById(long workerId) {

        try {
            Optional<Worker> entity = workerRepository.findById(workerId);
            if(entity.isEmpty()) {
                return new SettingWorkerResponseDto(false,3004,"해당하는 근무근로자 정보가 없음", null);
            }
            workerRepository.deleteById(workerId);
            return new SettingWorkerResponseDto(true,1058,"근무지 삭제 성공", null);
        }   catch (Exception e){
            System.out.println(e);
            return new SettingWorkerResponseDto(false,3067,"근무지 삭제 실패",null);
        }
    }

}
