package com.llxx.utils;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PassUtils {
        /**
         * 加密密码
         * @param plainPassword 明文密码
         * @return 加密后的密码*/

        public static String encryptPassword(String plainPassword) {
            return BCrypt.hashpw(plainPassword,BCrypt.gensalt());
        }
        /**
         * 验证密码
         * @param plainPassword 明文密码
         * @param encryptedPassword 加密后的密码
         * @return 验证结果*/
        public static boolean verifyPassword(String plainPassword, String encryptedPassword) {
            return  BCrypt.checkpw(plainPassword,encryptedPassword);
        }
}

