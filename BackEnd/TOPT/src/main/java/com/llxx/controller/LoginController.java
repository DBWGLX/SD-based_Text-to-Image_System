package com.llxx.controller;

import com.llxx.pojo.Result;
import com.llxx.pojo.user;
import com.llxx.service.emailService;
import com.llxx.service.userService;
import com.llxx.utils.JwtUtils;
import com.llxx.utils.TotpUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    userService userservice;

    @Autowired
    HttpServletRequest request;

    @Autowired
    emailService emailservice;

    // 缓存用户 OTP 和生成时间，使用 ConcurrentHashMap 以支持多线程
    private final ConcurrentHashMap<String, String> userOtpCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> otpTimestampCache = new ConcurrentHashMap<>();

    //开始登录
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

    //登录后发送验证码到用户邮箱
    @PostMapping("/api/auth/totp/email")
    public Result send() {
        String jwt=request.getHeader("token");//拿到jwt
        Claims claims=JwtUtils.parseJWT(jwt);//解析jwt
        String email=(String) claims.get("email");//获取email
        log.info("email={}",email);
        String secretKey = "llxx"; // 这里可以为每个用户生成一个唯一密钥
        String otp = TotpUtils.generateTotp(secretKey); // 生成动态验证码
        log.info("验证码otp={}",otp);

        // 将 OTP 和生成时间存入缓存
        userOtpCache.put(email, otp);
        otpTimestampCache.put(email, System.currentTimeMillis()); // 记录生成时间
        log.info("缓存成功");

        // 发送 OTP 到用户邮箱

        emailservice.sendEmail(email, "文生图系统登录验证码", "您的验证码是: " + otp);
        log.info("发送成功");
        return Result.success(otp);//返回验证码
    }

    //验证验证码
    @PostMapping("/api/auth/totp/enable")
    public Result login2(@RequestParam String email, @RequestParam String otp) {
        String cachedOtp = userOtpCache.get(email); // 从缓存中获取用户的 OTP
        Long otpTimestamp = otpTimestampCache.get(email); // 获取 OTP 生成时间
        log.info("取出缓存成功");

        // 验证 OTP
        if (cachedOtp != null && otpTimestamp != null) {
            // 检查 OTP 是否在 60 秒内生成
            if (System.currentTimeMillis() - otpTimestamp <= 60000) {
                if (cachedOtp.equals(otp)) { // 验证通过
                    userOtpCache.remove(email); // 验证通过后，清除缓存
                    otpTimestampCache.remove(email);
                    log.info("验证成功");
                    return  Result.success();// 返回成功响应
                } else {
                    return  Result.error("验证码错误");// 验证失败
                }
            } else {
                return  Result.error("验证码超时");// 过期验证
            }
        }
        return  Result.error("验证码为空");// 需要先请求验证码
    }
}
