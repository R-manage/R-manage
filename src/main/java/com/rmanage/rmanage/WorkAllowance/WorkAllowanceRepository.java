package com.rmanage.rmanage.WorkAllowance;

import com.rmanage.rmanage.entity.WorkAllowance;
import com.rmanage.rmanage.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkAllowanceRepository extends JpaRepository<WorkAllowance, Long> {
    List<WorkAllowance> findByWorkerAndWorkDateBetween(Worker worker, LocalDate start, LocalDate end);
}
