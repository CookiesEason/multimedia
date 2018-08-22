package com.example.multimedia.service.impl;

import com.example.multimedia.domian.User;
import com.example.multimedia.service.MailService;
import com.example.multimedia.service.UserService;
import com.example.multimedia.util.EmailUtil;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.util.UserUtil;
import com.example.multimedia.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author CookiesEason
 * 2018/08/02 13:40
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    private static final Long CODEEXPIRETIME = 60000L;

    @Value("${mail.activateUrl}")
    private String activateUrl;

    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    @Override
    public ResultVo sendEmail(String username,String activateCode) {
        // TODO: 2018/08/02 应该要写一个邮件模板html来进行发送
        SimpleMailMessage message = new SimpleMailMessage();
        ValueOperations<String, String> valueStr = template.opsForValue();
        message.setFrom("837447352@qq.com");
        message.setTo(username);
        message.setSubject("激活邮件");
        valueStr.set(username,activateCode,1400,TimeUnit.MINUTES);
        message.setText(activateUrl+"username="+username+"&activeCode="+activateCode);
        try {
            mailSender.send(message);
            return ResultVoUtil.success();
        } catch (Exception e) {
            log.error("发送简单邮件时发生异常！", e);
        }
        return ResultVoUtil.error(0,"发送简单邮件时发生异常！");
    }

    @Override
    public ResultVo sendPasswordUpdateEmail() {
        String email = UserUtil.getUserName();
        SimpleMailMessage message = new SimpleMailMessage();
        String code = EmailUtil.generateCode();
        ValueOperations<String, String> valueStr = template.opsForValue();
        valueStr.set(email,code,CODEEXPIRETIME, TimeUnit.MILLISECONDS);
        message.setFrom("837447352@qq.com");
        message.setTo(email);
        message.setSubject("密码修改");
        message.setText("您的验证码为:" + code);
        try {
            mailSender.send(message);
            return ResultVoUtil.success();
        } catch (Exception e) {
            log.error("发送简单邮件时发生异常！", e);
        }
        return ResultVoUtil.error(0,"发送简单邮件时发生异常！");
    }

}
