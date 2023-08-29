package com.rmanage.rmanage.worker;

import com.rmanage.rmanage.UserRepository;
import com.rmanage.rmanage.WorkAllowance.Stat;
import com.rmanage.rmanage.WorkAllowance.WorkAllowanceService;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.WorkPlace;
import com.rmanage.rmanage.entity.Worker;
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

    private final WorkAllowanceService workAllowanceService;


    @Transactional
    public void save(WorkerSaveDto workerSaveDto) {

        User user = userRepository.findById(workerSaveDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다. id=" + workerSaveDto.getUserId()));
        //WorkPlace에 adminCode 필드 추가로 생성자에 초기화를 시켜주는 바람에 아래 코드 잠깐 주석처리 해놓겠습니다!
        WorkPlace workPlace = null;
//        WorkPlace workPlace = workPlaceRepository.save(new WorkPlace(workerSaveDto.getWorkPlaceName()));
        workerRepository.save(workerSaveDto.toEntity(user, workPlace));
    }

    @Transactional
    public void update(Long workerId, WorkerUpdateRequestDto workerUpdateRequestDto) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new IllegalArgumentException("근로자가 존재하지 않습니다. id=" + workerId));

        WorkPlace workPlace = worker.getWorkPlace();

        worker.update(workerUpdateRequestDto);
        workPlace.update(workerUpdateRequestDto.getWorkPlaceName());
    }

    @Transactional
    public void delete(Long workerId) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new IllegalArgumentException("근무지가 존재하지 않습니다."));

        workerRepository.delete(worker);

    }

    //상세 조회
    @Transactional(readOnly = true)
    public WorkerResponseDto getWorkerById(Long workerId) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new IllegalArgumentException("근무지가 존재하지 않습니다."));

        Stat stat = workAllowanceService.getStat(worker);

        WorkerResponseDto workerResponseDto = new WorkerResponseDto(worker);
        workerResponseDto.setStat(stat);

        return workerResponseDto;
    }

    //전체 조회
    @Transactional(readOnly = true)
    public List<WorkerResponseDto> getWorkersByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다. id=" + userId));

        return workerRepository.findByUser(user).stream()
                .map(WorkerResponseDto::new)
                .collect(Collectors.toList());

    }




}
