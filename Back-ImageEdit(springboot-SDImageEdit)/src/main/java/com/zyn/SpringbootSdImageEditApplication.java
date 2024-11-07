package com.zyn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


// 启动 Spring Boot 应用的主类
@SpringBootApplication // 启用组件扫描和自动配置
@ComponentScan("com.zyn.dao") // 指定要扫描的包
public class SpringbootSdImageEditApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSdImageEditApplication.class, args);
    }

}
