package com.rmanage.rmanage.worker;

import com.rmanage.rmanage.entity.User;
import com.rmanage.rmanage.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    List<Worker> findByUser(User user);
}
