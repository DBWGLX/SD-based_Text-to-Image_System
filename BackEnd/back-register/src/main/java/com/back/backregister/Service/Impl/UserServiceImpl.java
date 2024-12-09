package com.back.backregister.Service.Impl;

import com.back.backregister.Do.UserDo;
import com.back.backregister.Mapper.UserMapper;
import com.back.backregister.Mapper.VerifyCodeMapper;
import com.back.backregister.Service.UserService;
import com.back.backregister.dto.EmailDto;
import com.back.backregister.dto.LoginDto;
import com.back.backregister.dto.RegisterDto;
import com.back.backregister.dto.VerifyCodeDto;
import com.back.backregister.utils.JwtUtils;
import com.back.backregister.utils.PassUtils;
import com.back.backregister.utils.RandomUtils;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.*;

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
    @Autowired
    private VerifyCodeMapper verifyCodeMapper;
    @Autowired
    private SpringTemplateEngine templateEngine;

    // 存储邮箱和对应的验证码 --- 》 临时存储的 ---》改为存放到数据库中redis会更好，到时候修改一下。
    // private Map<String,String> verifyCodes = new HashMap<>();

    @Autowired
    private JavaMailSenderImpl mailSender;

    /**
     * 对包含 验证码的请求进行处理。
     *
     * @param registerDto web端传进来的参数。
     * @return 返回 用户的id,感觉有点多余
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String register(RegisterDto registerDto) {
        // 如果已经存在用户了，则无法注册
        log.info("要注册的用户信息：{}，{}，{}",registerDto.getUsername(),registerDto.getPassword(),registerDto.getEmail());
        if(userMapper.getByUserNameOrEmail(registerDto.getUsername()) != null)
        {
            return null;
        }else{ // 没有找到用户
            log.info("用户不存在，可以注册！");
            //这里去获取user里的code
            // String code = verifyCodes.get(registerDto.getEmail());

            // 验证是否过期
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime experation = verifyCodeMapper.getExperationTime(registerDto.getEmail());
            if(experation!= null && now.isAfter(experation))
            {
                log.info("验证码已过期！");
                return "验证码已过期！";
            }
            // 验证code是否正确且验证对应的email(上面获取code的过程就判断了)
            // 该code需要从数据库中取
            String code = verifyCodeMapper.getCode(registerDto.getEmail());
            // 验证code是否正确且验证对应的email(上面获取code的过程就判断了)
            if(code != null && code.equals(registerDto.getCode()))
            {
                // 这里要处理user的password !
                String secretPass = PassUtils.encryptPassword(registerDto.getPassword());
                registerDto.setPassword(secretPass);
                userMapper.add(registerDto);
                return "注册成功！需要返回登录页面！";
            }
            // 注册失败
            return "注册失败！";
        }
    }

    /**
     * 点击发送验证码给对应的邮箱
     *
     * @param
     * @return
     */
    @Override
    @Transactional
    public String sendEmail(EmailDto emailDto) {

        //生成对应的验证码取uuid的0-6位
        String code = RandomUtils.getSixRandomCode();
        // 到时候这部分修改一下
        String subject = "文生图的验证码";
        log.info("验证码生成成功：{}",code);
        //存入到数据库中
        VerifyCodeDto verifyCodeDto = new VerifyCodeDto();
        verifyCodeDto.setCode(code);
        verifyCodeDto.setEmail(emailDto.getEmail());
        verifyCodeDto.setCreateTime(LocalDateTime.now());
        // 5分钟过期
        verifyCodeDto.setExpirationTime(LocalDateTime.now().plusMinutes(5));
        log.info("验证码生成时间：{}",verifyCodeDto.getCreateTime());
        log.info("验证码过期时间：{}",verifyCodeDto.getExpirationTime());

        // 这里要判断  邮箱是否已经存在
        if(verifyCodeMapper.getCode(emailDto.getEmail()) != null)
        {
            log.info("数据库中已有验证码！删除已有验证码！");
            // 这里要删除原来的验证码
            verifyCodeMapper.deleteCode(emailDto.getEmail());

        }
        verifyCodeMapper.insertCode(verifyCodeDto);
        log.info("数据存储成功");

        // 设置邮件的模板
        Context context = new Context();
        // 设置模板中的变量
        context.setVariable("verifyCode", Arrays.asList(code.split("")));
        String process = templateEngine.process("EmailVerificationCode.html",context);
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom("1320104286@qq.com");
            helper.setTo(emailDto.getEmail());
            helper.setSubject(subject);
            helper.setSentDate(new Date());
            helper.setText(process,true);

        }catch (Exception e)
        {
            e.printStackTrace();
            log.info("邮件发送异常！");
        }
        mailSender.send(mimeMessage);
        log.info("邮件发送成功");
        return "验证码发送成功！";
    }

    /**
     * 登录接口
     * @param loginDto 控制层传输过来的信息
     * @return 返回jwt
     */
    @Override
    public String login(LoginDto loginDto) {
        /*
        验证账号和密码，并生成jwt进行返回
         */
        // 这里应该换成dto,后面再修改,还有VO
        // 密码加密再进行查询
        UserDo userDo = userMapper.checkLogin(loginDto);
        log.info("要登录的用户:{}",userDo);
        log.info("验证是否通过：{}",PassUtils.verifyPassword(loginDto.getPassword(),userDo.getPassword()));
        if(PassUtils.verifyPassword(loginDto.getPassword(),userDo.getPassword()))
        {
            log.info("登录成功设置返回jwt");
            // 并设置为普通用户
            return JwtUtils.generateJwt(loginDto.getUsername(),"user");
        }
        log.info("账号或密码错误!");
        return null;
    }

}
