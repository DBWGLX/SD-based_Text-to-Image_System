package com.llxx.utils;

import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailUtil {
    // 配置发送者的邮箱和密码
    String emailNum = "2124760136@qq.com"; // 发送者邮箱
    String password = "bhpxfjlkjefldbea"; // 发送者第三方凭证，不是密码
    //发送邮箱
    public void sendEmail(String to, String subject, String body) {
        // 设置邮件服务器的属性
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); // 启用认证
        props.put("mail.smtp.starttls.enable", "true"); // 启用TLS
        props.put("mail.smtp.host", "smtp.qq.com"); // SMTP 服务器地址
        props.put("mail.smtp.port", "587"); // SMTP 端口

        // 创建会话并使用用户名和密码进行认证
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailNum, password);
            }
        });
        try {
            // 创建邮件消息
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailNum)); // 设置发送者
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); // 设置接收者
            message.setSubject(subject); // 设置邮件主题
            message.setText(body); // 设置邮件内容
            // 发送邮件
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // 打印异常信息
        }
    }
}
