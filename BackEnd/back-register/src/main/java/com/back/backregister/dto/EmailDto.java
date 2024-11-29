package com.back.backregister.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ author: rose
 * @ date: 2024-11-20
 * @ version: 1.0
 * @ description: 用以发送邮件进行验证
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {
    @NotNull(message = "注册邮箱不能为空")
    private String email;
}
