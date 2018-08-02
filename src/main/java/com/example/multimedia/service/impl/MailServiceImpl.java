package com.example.multimedia.service.impl;

import com.example.multimedia.service.MailService;
import com.example.multimedia.util.ResultVoUtil;
import com.example.multimedia.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author CookiesEason
 * 2018/08/02 13:40
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Value("${mail.activateUrl}")
    private String activateUrl;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public ResultVo sendEmail(String email,String username,String activateCode) {
        // TODO: 2018/08/02 应该要写一个邮件模板html来进行发送
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("837447352@qq.com");
        message.setTo(email);
        message.setSubject("激活邮件");
        message.setText(activateUrl+"username="+username+"&activeCode="+activateCode);
        try {
            mailSender.send(message);
            return ResultVoUtil.success();
        } catch (Exception e) {
            log.error("发送简单邮件时发生异常！", e);
        }
        return ResultVoUtil.error(0,"发送简单邮件时发生异常！");
    }
}
