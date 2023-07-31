package com.rmanage.rmanage;

import com.rmanage.rmanage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
