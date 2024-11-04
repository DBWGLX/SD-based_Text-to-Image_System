package com.zyn.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.zyn")
public class AppConfig {
    // 其他Bean定义
}