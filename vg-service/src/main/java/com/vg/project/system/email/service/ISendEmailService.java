package com.vg.project.system.email.service;

public interface ISendEmailService {
    public Boolean sendSimpleEmail(String sender, String recipient, String subject, String content);
}
