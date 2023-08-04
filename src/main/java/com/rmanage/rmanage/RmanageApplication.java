package com.rmanage.rmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RmanageApplication {

	public static void main(String[] args) {
		SpringApplication.run(RmanageApplication.class, args);
	}

}
