package com.llxx.controller;

import com.llxx.pojo.Result;
import com.llxx.pojo.user;
import com.llxx.service.userService;
import com.llxx.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
//本类仅用于测试jwt令牌技术，获取jwt令牌封装在token中测试历史记录的获取
public class LoginController {
    @Autowired
    userService userservice;

    @PostMapping("/api/auth/login")
    public Result login(@RequestBody user u) {
        log.info("用户登录：",u);
        user u2=userservice.login(u);
        //登录成功下发jwt令牌
        if(u2!=null){
            log.info("u2={}",u2);
            Map<String,Object> map=new HashMap<>();
            map.put("userId",u2.getUserId());
            map.put("username",u2.getUsername());
            map.put("email",u2.getEmail());
            String jwt= JwtUtils.generateJwt(map);
            return Result.success(jwt);
        }
        return Result.error("用户名或者密码错误");
    }
}
