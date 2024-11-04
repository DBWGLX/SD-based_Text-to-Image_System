package com.back.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Token验证的工具类
 */
public class TokenUtil {

    public final static String SUCCESS = "Token success";
    public final static String Fail = "Token fail";
    //token秘钥,请勿泄露,请勿随便更改
    private static final String SECRET = "JKKLJOKADBCA";
    //token过期时间为6天
    public static final int calendarField = Calendar.DATE;
    public static final int calendarInterval = 6;

    /**
     * 根据用户ID和角色来生成Token
     *
     * @param acUserId
     * @return
     * @throws Exception
     */
    public static String createToken(String acUserId) throws Exception {
        //token签发时间
        Date iatDate = new Date();
        // token失效时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        // token的header
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // 创建 token
        // param backups {iss:Service, aud:APP}
        // header头部
        String token = JWT.create().withHeader(map)
                // payload载荷
                .withClaim("iss", "Service")
                .withClaim("aud", "APP")
                .withClaim("userId", acUserId)
                .withClaim("exp", expiresDate)
                .withClaim("iat", iatDate)
                // sign time
                .withIssuedAt(iatDate)
                // expire time
                .withExpiresAt(expiresDate)
                // signature签名
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    /**
     * 根据Token来获取信息
     *
     * @param token
     * @return
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        try {
            jwt = verifier.verify(token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return jwt.getClaims();
    }


    /**
     * 从token中获取UserId,
     *
     * @param token 用户的token的值
     * @return
     * @throws Exception 抛出运行时异常
     */
    public static String getAcUserId(String token) {
        Map<String, Claim> claimMap = verifyToken(token);
        if(claimMap == null){
            return null;
        }
        Claim userClaim = claimMap.get("userId");
        if (null == userClaim || StringUtils.isEmpty(userClaim.asString())) {
            return null;
        }
        return userClaim.asString();
    }

    /**
     * 从token中获取过期日期,
     *
     * @param token 用户的token的值
     * @return
     * @throws Exception 抛出运行时异常
     */
    public static Date getExpTime(String token) {
        Map<String, Claim> claimMap = verifyToken(token);
        if(claimMap == null){
            return null;
        }
        Claim expClaim = claimMap.get("exp");
        if (null == expClaim) {
            return null;
        }
        return expClaim.asDate();
    }
}
