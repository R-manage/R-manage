package com.rmanage.rmanage.worker;

import com.rmanage.rmanage.UserRepository;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.WorkPlace;
import com.rmanage.rmanage.workPlace.WorkPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor


public class WorkerService {

    private final WorkerRepository workerRepository;
    private final UserRepository userRepository;
    private final WorkPlaceRepository workPlaceRepository;

    public void save(WorkerSaveDto workerSaveDto) {

        userRepository.save(User.builder()
                        .email("email")
                        .isEmployee(true)
                        .phoneAuthDate("2023-03-03")
                        .nickname("oyun")
                        .password("password")
                        .phoneCode(1111)
                        .phoneNumber("1111")
                .build());

        User user = userRepository.findById(workerSaveDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다. id=" + workerSaveDto.getUserId()));

        WorkPlace workPlace = workPlaceRepository.save(new WorkPlace(workerSaveDto.getName()));
        workerRepository.save(workerSaveDto.toEntity(user, workPlace));
    }


    public void update(WorkPlace workPlace) {

    }


    public void delete(WorkPlace workPlace) {

    }

    //상세 조회
    @Transactional(readOnly = true)
    public WorkerResponseDto getWorkerById(Long workerId) {

        return workerRepository.findById(workerId)
                .map(WorkerResponseDto::new)
                .orElseThrow(()-> new IllegalArgumentException("근무지가 존재하지 않습니다."));


    }

    //전체 조회
    @Transactional(readOnly = true)
    public List<WorkerResponseDto> getWorkerByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다. id=" + userId));

        return workerRepository.findByUser(user).stream()
                .map(WorkerResponseDto::new)
                .collect(Collectors.toList());

    }
}
