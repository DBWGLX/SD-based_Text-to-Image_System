package com.back.backregister.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ author: rose
 * @ date: 2024-11-22
 * @ version: 1.0
 * @ description: 用于存储验证码的dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyCodeDto {
    private int id;
    private String email;
    private String code;
    private LocalDateTime createTime;
    private LocalDateTime expirationTime;
}
