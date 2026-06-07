package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import jakarta.annotation.PostConstruct;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class DemoApplication {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void debugEnv() {

        System.out.println("=== DEBUG: Spring Environment ===");

        System.out.println("spring.mongodb.uri = " + env.getProperty("spring.data.mongodb.uri"));
        System.out.println("spring.data.mongodb.database = " + env.getProperty("spring.data.mongodb.database"));
        System.out.println("spring.mongodb.ssl.enabled = " + env.getProperty("spring.data.mongodb.ssl.enabled"));

    }
}
