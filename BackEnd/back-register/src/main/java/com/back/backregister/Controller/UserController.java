package com.back.backregister.Controller;

import com.back.backregister.Service.UserService;
import com.back.backregister.Do.Result;
import com.back.backregister.dto.EmailDto;
import com.back.backregister.dto.RegisterDto;
import com.back.backregister.dto.LoginDto;
import jakarta.mail.Header;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ author: rose
 * @ date: 2024-11-14
 * @ version: 1.0
 * @ description:
 */
@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto)
    {
        log.info("登录的账号：{}，{}",loginDto.getUsername(),loginDto.getPassword());
        String jwt = userService.login(loginDto);
        if(jwt != null)
        {
            //登录成功为用户设置header token = jwt
            // Header header = new Header("token",jwt);
            return Result.success(jwt);
        }
        return Result.error("登录失败");
    }

    @PostMapping("/register")
    public Result add(@RequestBody RegisterDto registerDto)
    {   String message = userService.register(registerDto);
        if( message.equals("注册成功！需要返回登录页面！"))
        {
            return Result.success(message);
        }
        return Result.error(message);
    }

    @PostMapping("/verify")
    public Result add(@RequestBody EmailDto emailDto)
    {
        log.info("给邮箱{}发送验证码",emailDto.getEmail());
        userService.sendEmail(emailDto);
        return Result.success("邮件已发送");
    }
}
