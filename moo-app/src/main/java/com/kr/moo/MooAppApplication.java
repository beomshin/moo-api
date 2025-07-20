package com.kr.moo;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MooAppApplication {

    @PostConstruct
    public void init() {
        SharedTestClass.run();
        CoreTestClass.run();
    }

    public static void main(String[] args) {
        SpringApplication.run(MooAppApplication.class, args);
    }
}
