package com.rmanage.rmanage.worker;

import com.rmanage.rmanage.entity.WorkPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

}
