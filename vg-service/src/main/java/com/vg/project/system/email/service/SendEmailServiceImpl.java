package com.vg.project.system.email.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class SendEmailServiceImpl implements ISendEmailService {
    @Autowired
    private JavaMailSender mailSender;

    /***
     * 发送纯文本内容邮件
     * @param sender  发送者邮箱
     * @param recipient 接收者邮箱
     * @param subject  主题，标题
     * @param content   内容
     * @return  是否发送成功
     */
    @Override
    public Boolean sendSimpleEmail(String sender, String recipient, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(recipient);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
            return true;
        } catch (MailException e) {
            e.printStackTrace();
            return false;
        }
    }



}
