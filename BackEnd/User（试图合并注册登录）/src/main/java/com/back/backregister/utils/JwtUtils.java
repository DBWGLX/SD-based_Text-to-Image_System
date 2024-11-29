package com.back.backregister.utils;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
* @ author: rose
* @ date: 2024-11-19
* @ version: 1.0
* @ description: 用以登录成功后生成jwt,   12个小时后就会过期，需要重新登录。
*/
@Data
@Component
@ConfigurationProperties(prefix = "jwt")  // 从配置文件中获取相关的属性值
public class JwtUtils {
    // 签名算法需要的密钥
    private static long expiration = 12 * 60*60*1000; //有效期是12小时
    private static SecretKey key = Jwts.SIG.HS256.key().build(); //密钥是随机生成的。

    /**
     * 生成token
     * @param name 用户名
     * @param subject 用户类型
     * @return 返回token
     */
    public static String generateJwt(String name, String subject)
    {
        String jwt = Jwts.builder()
                .subject(subject) // 设置主题一般是用户类型
                .issuedAt(new Date())
                .claim("name",name)
                .signWith(key)
                .expiration(new Date(System.currentTimeMillis()+expiration)) // 设置有效期
                .compact();
        return jwt;
    }

    /**
     *
     * @param jws jws字符串
     * @return 返回一个对象
     */
     public static Object parseJwt(String jws)
     {
         return  Jwts.parser()
                 .verifyWith(key)
                 .build()
                 .parseSignedClaims(jws);
     }
}
