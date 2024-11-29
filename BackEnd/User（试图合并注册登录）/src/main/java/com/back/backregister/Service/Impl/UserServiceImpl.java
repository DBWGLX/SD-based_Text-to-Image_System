package com.back.backregister.Service.Impl;

import com.back.backregister.Mapper.UserMapper;
import com.back.backregister.Service.UserService;
import com.back.backregister.pojo.User;

import com.back.backregister.utils.JwtUtils;
import com.back.backregister.utils.PassUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ author: rose
 * @ date: 2024-11-14
 * @ version: 1.0
 * @ description:
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    // 存储邮箱和对应的验证码
    private Map<String,String> verifyCodes = new HashMap<>();
    @Autowired
    private JavaMailSenderImpl mailSender;

    /**
     * 对包含 验证码的请求进行处理。
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer register(User user) {
        // 如果已经存在用户了，则无法注册
        log.info("要注册的用户信息：{}，{}，{}",user.getUsername(),user.getPassword(),user.getEmail());
        if(userMapper.getByUserNameOrEmail(user.getUsername()) != null)
        {
            return null;
        }else{ // 没有找到用户
            log.info("用户不存在，可以注册！");
            //这里去获取user里的code
            String code = verifyCodes.get(user.getEmail());
            // 验证code是否正确且验证对应的email(上面获取code的过程就判断了)
            if(code != null && code.equals(user.getCode()))
            {
                // 这里要处理user的password !
                String secretPass = PassUtils.encryptPassword(user.getPassword());
                user.setPassword(secretPass);
                userMapper.add(user);
                return userMapper.getIdByUserName(user);
            }
            // 注册失败
            return null;
        }
    }

    /**
     * 点击发送验证码给对应的邮箱
     * @param email
     */
    @Override
    public void sendEmail(String email) {

        //生成对应的验证码取uuid的0-6位
        String code = UUID.randomUUID().toString().substring(0,6);
        verifyCodes.put(email,code);
        // 到时候这部分修改一下
        String subject = "注册文生图系统的验证码";
        String message = "验证码为："+code;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("1320104286@qq.com");
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    

}
