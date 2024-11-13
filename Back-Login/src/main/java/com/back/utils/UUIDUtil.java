package com.back.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


public class UUIDUtil {

    /**
     * 获得一个uuid
     *
     * @return String
     * @throws InterruptedException
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获得一个唯一码
     *
     * @return String
     * @throws InterruptedException
     */
    public static String getUniqueCode() {
        synchronized ("UniqueCode") {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(e);
            }

            return (new SimpleDateFormat("yyyyMMdd").format(new Date())) + getSJNum(6);
        }
    }


    /**
     * 获得一个唯一码（利用时间戳）
     *
     * @return String
     * @throws InterruptedException
     */
    public static String getUniqueCodeWithTime() {
        long curTime = System.currentTimeMillis();
        return Long.toString(curTime,Character.MAX_RADIX).toUpperCase();
    }


    /**
     * 获得随机码
     *
     * @return String
     * @throws InterruptedException
     */
    public static String getSJCode(char[] codeSequence, int length) {
        if (codeSequence == null || codeSequence.length == 0) {
            codeSequence = new char[]{
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L',
                    'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                    'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                    'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                    'x', 'y', 'z', '2', '3', '4', '5', '6', '7', '8', '9'};
        }
        StringBuffer code = new StringBuffer();
        //创建一个随机数生成器类
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            code.append(codeSequence[random.nextInt(codeSequence.length)]);
        }

        return code.toString();
    }

    /**
     * 获得数字随机码
     *
     * @return String
     * @throws InterruptedException
     */
    public static String getSJNum(int length) {
        char[] codeSequence = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        return getSJCode(codeSequence, length);
    }


}
