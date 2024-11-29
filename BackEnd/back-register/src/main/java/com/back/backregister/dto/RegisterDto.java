package com.back.backregister.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ author: rose
 * @ date: 2024-11-20
 * @ version: 1.0
 * @ description:
 */
@Data
@AllArgsConstructor
public class RegisterDto {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String code;
}
