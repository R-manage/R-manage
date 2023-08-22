package com.rmanage.rmanage.mypage;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.WorkAllowance;
import com.rmanage.rmanage.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyPageRepository extends JpaRepository<Worker, Long> {
    Worker findWorkerByWorkerId(Long workerId);
    User findUserByUserId(Long userId);
    List<Worker> findWorkerByAdminCode(String adminCode);
    List<WorkAllowance> findWorkAllowancesByWorker(Worker worker);
    User findUserByWorker(Worker worker);

}
