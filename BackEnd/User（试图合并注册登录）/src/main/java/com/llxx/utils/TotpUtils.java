package com.llxx.utils;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Base64;

@Component
public class TotpUtils {

    // 使用 HMAC-SHA1 算法生成 TOTP
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    // 生成 TOTP 的方法
    public static String generateTotp(String secret) {
        // 获取当前时间的时间索引（以 30 秒为单位）
        long timeIndex = Instant.now().getEpochSecond() / 30;
        return generateHmacSha1(secret, timeIndex); // 生成 HMAC-SHA1 值
    }

    // 使用 HMAC-SHA1 算法生成动态密码
    private static String generateHmacSha1(String secret, long timeIndex) {
        try {
            // 将密钥解码为字节数组
            byte[] key = Base64.getDecoder().decode(secret); // 假设密钥为 Base64 编码
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM); // 创建 HMAC-SHA1 实例
            mac.init(new SecretKeySpec(key, HMAC_SHA1_ALGORITHM)); // 初始化密钥
            byte[] hash = mac.doFinal(Long.toString(timeIndex).getBytes()); // 计算 HMAC

            // 提取动态截取
            int offset = hash[hash.length - 1] & 0xf; // 动态偏移量
            int binary = ((hash[offset] & 0x7f) << 24) | // 将字节转换为整数
                    ((hash[offset + 1] & 0xff) << 16) |
                    ((hash[offset + 2] & 0xff) << 8) |
                    (hash[offset + 3] & 0xff);
            int otp = binary % 1000000; // 生成 6 位数的 OTP
            return String.format("%06d", otp); // 格式化为 6 位数字符串
        } catch (Exception e) {
            throw new RuntimeException(e); // 异常处理
        }
    }
}

