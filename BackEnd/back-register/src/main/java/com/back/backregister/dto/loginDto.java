package com.back.backregister.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ author: rose
 * @ date: 2024-11-19
 * @ version: 1.0
 * @ description: 用于接收前端发送来的数据。利用@NotNull 进行非空校验
 */
@Data
@AllArgsConstructor
public class LoginDto {
    @NotNull(message = "用户名不能为空")
    private String username;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "验证码id不能为空")
    private String captchaId;
    @NotNull(message = "验证码内容不能为空")
    private String captcha;
}
