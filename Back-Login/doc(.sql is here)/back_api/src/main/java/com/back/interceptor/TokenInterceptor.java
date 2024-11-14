package com.back.interceptor;


import com.alibaba.fastjson.JSON;
import com.back.core.Result;
import com.back.core.ResultCode;
import com.back.core.ResultMassage;
import com.back.model.Admins;
import com.back.model.vo.User;
import com.back.service.AdminsService;
import com.back.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;


@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private AdminsService adminsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            retResponse(response, ResultCode.TOKEN_ERROR, ResultMassage.TOKEN_ERROR.msg());
            return false;
        }
        String userId = TokenUtil.getAcUserId(token);
        Date expTime = TokenUtil.getExpTime(token);
        if (userId == null || expTime == null) {
            retResponse(response, ResultCode.TOKEN_ERROR, ResultMassage.TOKEN_ERROR.msg());
            return false;
        }
        User tuUser = null;
        Admins admins = adminsService.findById(userId);
        if (admins != null) {
            tuUser = new User();
            tuUser.setId(admins.getId());
            tuUser.setUsername(admins.getUsername());
        }
        if (tuUser == null) {
            retResponse(response, ResultCode.TOKEN_ERROR, ResultMassage.TOKEN_ERROR.msg());
            return false;
        }
        int compare = DateUtils.truncatedCompareTo(new Date(), expTime, Calendar.MILLISECOND);
        if (compare == 1 || compare == 0) {
            retResponse(response, ResultCode.TOKEN_ERROR, ResultMassage.TOKEN_ERROR.msg());
            return false;
        }
        request.setAttribute("loginUser", tuUser);
        log.info("已登录,用户信息为{}", tuUser.getUsername());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private void retResponse(HttpServletResponse response, ResultCode code, String message) throws IOException {
        Result result = new Result();
        result.setCode(code).setMessage(message);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        response.getWriter().write(JSON.toJSONString(result));
    }

}
