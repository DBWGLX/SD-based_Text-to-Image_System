package com.back.backregister.Vo;

import lombok.Builder;
import lombok.Data;

/**
* @ author: rose
* @ date: 2024-11-25
* @ version: 1.0
* @ description: 数据对象
*/
@Data
@Builder
public class CaptcahVO{
    // 验证码id
    private String captchaId;
    // 验证码图片的base64
    private String captchaImage;
}
