package com.rmanage.rmanage.mypage;
import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.WorkAllowance;
import com.rmanage.rmanage.entity.WorkPlace;
import com.rmanage.rmanage.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@EnableJpaRepositories
public interface MyPageRepository extends JpaRepository<Worker, Long> {
    Worker findWorkerByWorkerId(Long workerId);
    List<Worker> findWorkerByWorkPlace(WorkPlace workPlace);
    User findUserByWorkerId(Long workerId);

}
