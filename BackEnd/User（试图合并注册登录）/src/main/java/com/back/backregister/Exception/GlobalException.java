package com.back.backregister.Exception;

import com.back.backregister.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ author: rose
 * @ date: 2024-11-20
 * @ version: 1.0
 * @ description: 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalException {

    // 处理所有异常
    @ExceptionHandler(Exception.class)
    public Result ex(Exception e)
    {
        e.printStackTrace();
        return Result.error("对不起,操作失败,请联系管理员!");
    }
}
