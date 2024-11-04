package com.example.demo.controller;

import com.example.demo.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class RequestController {
    @RequestMapping("/simpleParam")
    public String simpleParam(String name,Integer age){
        //获取请求参数

        System.out.println(name + ":" + age);
        return "Ok!!!";
    }
    @RequestMapping("/simplePojo")
    public String simplePojo(User user){
        System.out.println(user);
        return "ok";
    }

    @RequestMapping("/dataParam")
    public String dataParam(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                LocalDateTime updatatTime){
        System.out.println(updatatTime);
        return "ok";
    }

    @RequestMapping("/jsonParam")
    public String jsonParam(@RequestBody User user){
        System.out.println(user);
        return "ok";
    }
}
