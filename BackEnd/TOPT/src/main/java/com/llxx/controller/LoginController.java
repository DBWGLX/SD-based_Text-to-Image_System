package com.llxx.controller;

import com.llxx.pojo.Result;
import com.llxx.pojo.UserMsg;
import com.llxx.pojo.Users;
import com.llxx.service.userService;
import com.llxx.utils.EmailUtil;
import com.llxx.utils.JwtUtils;
import com.llxx.utils.PassUtils;
import com.llxx.utils.TotpUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {
    @Autowired
    userService userservice;

    @Autowired
    HttpServletRequest request;

    @Autowired
    EmailUtil emailutil;

    // 缓存用户 OTP 和生成时间，使用 ConcurrentHashMap 以支持多线程
    private final ConcurrentHashMap<String, String> userOtpCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> otpTimestampCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String,Users>loginUserCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String,String> JWTCache=new ConcurrentHashMap<>();//缓存jwt，用username匹配

    //登录后发送验证码到用户邮箱
    @PostMapping("/api/auth/login/totp")
    public Result send(@RequestBody Users user) {
        System.out.println(114514);
        if(user==null){
            return Result.error("登录信息为空");
        }
        Users loginUser=userservice.login(user);//查找
        //查不到该用户或者加密后密码的和查询的的不一样
        if(loginUser==null|| !PassUtils.verifyPassword(user.getPassword(),loginUser.getPassword())){
            return Result.error("用户名或者密码错误");
        }
        String email=loginUser.getEmail();
        loginUserCache.put(loginUser.getUsername(),loginUser);
        String secretKey = "llxx"; // 这里可以为每个用户生成一个唯一密钥
        String otp = TotpUtils.generateTotp(secretKey); // 生成动态验证码
        log.info("验证码otp={}",otp);

        // 将 OTP 和生成时间存入缓存
        userOtpCache.put(email, otp);
        otpTimestampCache.put(email, System.currentTimeMillis()); // 记录生成时间
        log.info("缓存成功");
        // 发送 OTP 到用户邮箱
        emailutil.sendEmail(email, "文生图系统登录验证码", "您的验证码是: " + otp);
        log.info("发送成功");
        return Result.success();//返回验证码
    }

    //验证验证码
    @PostMapping("/api/auth/login")
    public Result login2(@RequestBody Users user) {
        Users loginUser=loginUserCache.get(user.getUsername());//缓存中读取
        loginUserCache.remove(user.getUsername());//去除缓存
        if(loginUser==null){
            return Result.error("验证失败");
        }
        String email=loginUser.getEmail();
        String otp=user.getOtp();
        String cachedOtp = userOtpCache.get(email); // 获取缓存中的 OTP
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
                    Map<String,Object> map=new HashMap<>();
                    map.put("userId",loginUser.getUserId());
                    map.put("email",loginUser.getEmail());
                    String jwt= JwtUtils.generateJwt(map);
                    JWTCache.put(loginUser.getUsername(),jwt);//缓存jwt
                    UserMsg userMsg=new UserMsg().setUserId(loginUser.getUserId()).setJwt(jwt);
                    return  Result.success(userMsg);// 返回成功响应
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
