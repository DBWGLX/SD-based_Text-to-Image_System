package com.llxx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.llxx.mapper")
public class SaveImageApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaveImageApplication.class, args);
    }

}
