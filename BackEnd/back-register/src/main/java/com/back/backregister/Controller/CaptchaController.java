package com.back.backregister.Controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.back.backregister.Vo.CaptcahVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

/**
 * @ author: rose
 * @ date: 2024-11-25
 * @ version: 1.0
 * @ description: 验证码接口相关操作
 */
@RestController
@RequestMapping("/captcha")
@Tag(name = "验证码接口",description = "验证码接口相关操作")
public class CaptchaController {
    // 整合redis

    @PostMapping
    @Operation(summary = "获取验证码")
    public CaptcahVO getCaptcha(String captchaId)
    {
        // 创建一个图像验证码，宽度130、高度为48 包含4个字符，干扰线10
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(130, 48, 4, 10);
        // 获取文本和base64编码
        String code = circleCaptcha.getCode();
        String imageBase64 = circleCaptcha.getImageBase64();
        // 如果没传入参数，就随机生成一个uuid 作为id
        captchaId = Optional.ofNullable(captchaId).orElseGet(() -> UUID.randomUUID().toString());
        // 讲id\code 保存到redis里，待完成


        return CaptcahVO.builder()
                .captchaId(captchaId)
                .captchaImage(imageBase64)
                .build();
    }
}
