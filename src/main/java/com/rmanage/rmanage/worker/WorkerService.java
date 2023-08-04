package com.rmanage.rmanage.worker;

import com.rmanage.rmanage.UserRepository;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.WorkPlace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor


public class WorkerService {

    private final WorkerRepository workerRepository;
    private final UserRepository userRepository;

    public void save(WorkerSaveDto workerSaveDto) {

        User user = userRepository.findById(workerSaveDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다. id=" + workerSaveDto.getUserId()));

        workerRepository.save(workerSaveDto.toEntity(user));
    }


    public void update(WorkPlace workPlace) {

    }


    public void delete(WorkPlace workPlace) {

    }
}
