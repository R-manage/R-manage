package com.rmanage.rmanage.mypage;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyPageRepository extends JpaRepository<Worker, Long> {
    List<Worker> findWorkerByWorkerId(Long workerId);
    List<User> findUserByUserUserId(Long userId);

}
