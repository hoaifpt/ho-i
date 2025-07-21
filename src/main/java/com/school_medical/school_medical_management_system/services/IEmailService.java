package com.school_medical.school_medical_management_system.services;

import java.util.concurrent.CompletableFuture;

public interface IEmailService {
    CompletableFuture<Void> sendEmail(String to, String subject, String text);
    String getOtpLoginEmailTemplate(String name, String accountNumber, String otp);

}
