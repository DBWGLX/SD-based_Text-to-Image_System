package com.back.backregister.Interceptor;

import ch.qos.logback.core.util.StringUtil;

import com.back.backregister.pojo.Result;
import com.back.backregister.utils.JwtUtils;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @ author: rose
 * @ date: 2024-11-20
 * @ version: 1.0
 * @ description:
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        Gson gson = new Gson();
        String jwt =  req.getHeader("token");

        // 1 判断是否存在 jwt 不存在则返回错误结果
        if(StringUtil.isNullOrEmpty(jwt))
        {
            log.info("token为空，返回未登录信息！");
            Result error = Result.error("NOT_LOGIN");
            // 手动转成json对象   -- 阿里巴巴 的fastJson
            String notLogin = gson.toJson(error);
            resp.getWriter().write(notLogin);
            return false;
        }
        // 5 判断jwt是否被篡改或者失效
        try{
            JwtUtils.parseJwt(jwt);
        }catch (Exception e)
        {
            log.info("jwt被篡改或失效，返回登录信息");
            Result error = Result.error("NOT_LOGIN");
            // 手动转成json对象   -- 阿里巴巴 的fastJson
            String notLogin = gson.toJson(error);
            resp.getWriter().write(notLogin);
            return false;
        }
        // 6 放行
        log.info("令牌合法！放行！");
        return true;
    }
}
