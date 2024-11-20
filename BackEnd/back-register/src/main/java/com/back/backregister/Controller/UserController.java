package com.back.backregister.Controller;

import com.back.backregister.Service.UserService;
import com.back.backregister.pojo.Result;
import com.back.backregister.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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



    @PostMapping("/register")
    public Result add(@RequestBody User user)
    {   Integer Id = userService.register(user);
        if( Id== null)
        {
            return Result.error("用户名或邮箱已经存在！注册失败！");
        }
        return Result.success("用户创建成功，id为:"+Id);
    }

    @PostMapping("/verify")
    public Result add(String email)
    {
        log.info("给邮箱{}发送验证码",email);
        userService.sendEmail(email);
        return Result.success("邮件已发送");
    }
}
