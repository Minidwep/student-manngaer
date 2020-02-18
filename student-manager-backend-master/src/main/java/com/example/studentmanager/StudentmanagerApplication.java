package com.example.studentmanager;

import com.example.studentmanager.security.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class StudentmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentmanagerApplication.class, args);
    }

}
