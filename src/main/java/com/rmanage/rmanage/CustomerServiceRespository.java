package com.rmanage.rmanage;

import com.rmanage.rmanage.entity.CustomerService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerServiceRespository extends JpaRepository<CustomerService, Long> {
}
