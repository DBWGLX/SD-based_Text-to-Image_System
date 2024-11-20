package com.back.backregister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;



@SpringBootApplication
public class BackRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackRegisterApplication.class, args);
    }
}
