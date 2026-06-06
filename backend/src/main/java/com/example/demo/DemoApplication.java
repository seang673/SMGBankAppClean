package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class DemoApplication {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @PostConstruct
    public void debugEnv() {
        System.out.println("=== DEBUG: Spring Environment ===");
        System.out.println("spring.application.name = " + env.getProperty("spring.application.name"));
        System.out.println("spring.mongodb.uri = " + env.getProperty("spring.mongodb.uri"));
        System.out.println("spring.mongodb.database = " + env.getProperty("spring.mongodb.database"));
    }
}
