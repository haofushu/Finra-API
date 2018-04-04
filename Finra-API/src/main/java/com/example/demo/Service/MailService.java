package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(String des, String subject, String text) throws javax.mail.MessagingException {

        MimeMessage Message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(Message, true);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setTo(des);
        mimeMessageHelper.setText(text, true);

        javaMailSender.send(Message);
    }
}
