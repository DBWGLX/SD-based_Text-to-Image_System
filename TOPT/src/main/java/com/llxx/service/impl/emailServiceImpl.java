package com.llxx.service.impl;

import com.llxx.service.emailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import java.util.Properties;

@Service
@Slf4j
public class emailServiceImpl implements emailService {

    @Override
    // 发送邮件的方法
    public void sendEmail(String to, String subject, String body) {
        // 设置邮件服务器的属性
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); // 启用认证
        props.put("mail.smtp.starttls.enable", "true"); // 启用TLS
        props.put("mail.smtp.host", "smtp.qq.com"); // SMTP 服务器地址
        props.put("mail.smtp.port", "587"); // SMTP 端口

        log.info("设置属性成功");
        log.info("email:{},password:{}",emailNum,password);
        log.info("to:{}",to);

        // 创建会话并使用用户名和密码进行认证
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailNum, password);
            }
        });
        log.info("认证成功");
        try {
            // 创建邮件消息
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailNum)); // 设置发送者
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); // 设置接收者
            message.setSubject(subject); // 设置邮件主题
            message.setText(body); // 设置邮件内容
            // 发送邮件
            Transport.send(message);
            log.info("发送成功");
        } catch (MessagingException e) {
            e.printStackTrace(); // 打印异常信息
        }
    }
}
