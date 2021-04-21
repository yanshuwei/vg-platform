package com.vg.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender mailSender;

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

    /**
     * 发送html邮件
     *
     * @param sender
     * @param recipient
     * @param subject
     * @param content
     */
    public void sendHtmlMail(String sender, String recipient, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(recipient);//邮件接收者
            helper.setSubject(subject);//邮件主题
            helper.setText(content, true);//邮件内容

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
