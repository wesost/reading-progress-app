package com.example.grclone.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;



@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.base-url}/api")
    private String baseUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    // email verification on signup
    public void sendVerificationEmail(String to, String token) {
        String subject = "Verify your account";
        String verificationUrl = baseUrl + "/users/verify?token=" + token;
        String text = "Welcome, please verify your email to complete signup by clicking the link below \n\n" + verificationUrl;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
