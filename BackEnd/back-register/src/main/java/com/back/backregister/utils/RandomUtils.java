package com.back.backregister.utils;

import java.util.Random;

/**
 * @ author: rose
 * @ date: 2024-11-22
 * @ version: 1.0
 * @ description: 用以随机获取数字，包含验证码
 */
public class RandomUtils {
    public static String getSixRandomCode()
    {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
