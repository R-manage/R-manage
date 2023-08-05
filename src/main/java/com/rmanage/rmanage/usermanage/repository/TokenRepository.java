package com.rmanage.rmanage.usermanage.repository;

import com.rmanage.rmanage.entity.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TokenRepository extends JpaRepository<Token, Long> {

}
