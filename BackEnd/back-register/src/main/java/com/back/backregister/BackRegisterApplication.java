package com.back.backregister;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@MapperScan("com.back.backregister.Mapper")
@SpringBootApplication
public class BackRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackRegisterApplication.class, args);
    }
}
