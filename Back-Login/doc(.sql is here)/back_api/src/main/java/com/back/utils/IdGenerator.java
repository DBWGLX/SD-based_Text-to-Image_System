package com.back.utils;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 基于雪花算法的思想定制化的一个id规则生成器
 *
 * 方案一：
 * 17位时间戳 + 3位序号 + 2位随机数 + 2位机器号  String类型  mysql存储用varchar     支持1000000QPS
 *
 * 方案二：
 * 14位时间戳 + 2位序号 + 2位随机数 + 1位机器号  Long类型  采用bigint类型存取      支持100QPS
 *
 * @author hcq
 * @date 2021/6/10 20:50
 */
public class IdGenerator {

    private final long workerId;

    private final int seqBit;

    private final int workBit;

    private final int randomBit;

    private long lastTimestamp = 0L;

    private long sequence = 0L;

    private static final BigInteger UNIT = new BigInteger("10");

    private final static DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    /**
     * @param workerId   机器序列号
     * @param workBit 机器序列号位数
     * @param seqBit 序列号位数
     * @param randomBit 随机数位数
     */
    public IdGenerator(long workerId, int seqBit, int workBit, int randomBit) {
        this.workerId = workerId;
        this.seqBit = seqBit;
        this.workBit = workBit;
        this.randomBit = randomBit;
        int maxValue = (UNIT.pow(workBit).intValue() - 1);
        if (workerId > maxValue || workerId < 0) {
            throw new IllegalArgumentException(String.format("workerId不能大于[%d]，且不能小于0", maxValue));
        }
    }

    /**
     * 获取下一个ID
     */
    public synchronized String nextId() {
        long timestamp = now();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("时钟倒退[%s]ms", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = sequence + 1;
            // 当前毫秒内计数满了，则等待下一毫秒
            if (sequence >  (UNIT.pow(seqBit).intValue()-1)) {
                timestamp = nextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;

        int random = ThreadLocalRandom.current().nextInt(0, UNIT.pow(randomBit).intValue());
        return String.format("%017d", timestamp) +
                String.format("%0"+seqBit+"d", sequence) +
                String.format("%0"+randomBit+"d", random) +
                String.format("%0"+workBit+"d", workerId) ;
    }

    private long nextMillis(final long lastTimestamp) {
        long timestamp = this.now();
        while (timestamp <= lastTimestamp) {
            timestamp = this.now();
        }
        return timestamp;
    }

    private long now() {
        LocalDateTime dateTime = LocalDateTime.now();
        return Long.parseLong(dateTime.format(FORMAT));
    }

}