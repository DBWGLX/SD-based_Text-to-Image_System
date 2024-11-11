package com.llxx.service;

public interface emailService {
    // 配置发送者的邮箱和密码
    String emailNum = "2124760136@qq.com"; // 发送者邮箱
    String password = "123"; // 发送者第三方凭证，不是密码
    //发送邮箱
    void sendEmail(String to, String subject, String body);
}
