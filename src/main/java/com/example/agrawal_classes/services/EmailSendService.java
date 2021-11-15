package com.example.agrawal_classes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class EmailSendService {
    @Autowired
    private JavaMailSender javaMailSender;

    void sendEmail(String email, String subject, String message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject(subject);
        msg.setText(message);
        javaMailSender.send(msg);
    }
}
