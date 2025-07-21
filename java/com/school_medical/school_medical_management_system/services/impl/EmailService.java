package com.school_medical.school_medical_management_system.services.impl;

import com.school_medical.school_medical_management_system.services.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EmailService implements IEmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public CompletableFuture<Void> sendEmail(String to, String subject, String text) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);

            future.complete(null); // Successful email sending
        } catch (MessagingException e) {
            e.printStackTrace();
            future.completeExceptionally(e); // Error occurred while sending
        }

        return future;
    }

    @Override
    public String getOtpLoginEmailTemplate(String name, String accountNumber, String otp) {
        return "<div>Your OTP: " + otp + "</div>";
    }


}
