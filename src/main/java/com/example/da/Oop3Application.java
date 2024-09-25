package com.example.da;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.da.repository")

public class Oop3Application {

    public static void main(String[] args) {
        SpringApplication.run(Oop3Application.class, args);
    }

}
