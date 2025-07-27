package com.kr.moo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.kr.moo.persistence.repository")
@EntityScan(basePackages = "com.kr.moo.persistence.entity")
public class MooAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MooAppApplication.class, args);
    }
}
