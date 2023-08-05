package com.rmanage.rmanage.usermanage.repository;

import com.rmanage.rmanage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<User, Long>{
  Optional<User> findByEmail(String email);
  Optional<User> findByPassword(String password);
}
