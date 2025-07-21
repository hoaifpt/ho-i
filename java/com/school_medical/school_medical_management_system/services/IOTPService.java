package com.school_medical.school_medical_management_system.services;

import com.school_medical.school_medical_management_system.repositories.entites.Appuser;

import java.util.concurrent.CompletableFuture;

public interface IOTPService {
    // Phương thức tạo OTP
    String generateOTP();

    // Phương thức lưu OTP vào cơ sở dữ liệu
    void saveOtp(int userId, String otp);

    // Phương thức xác thực OTP
    boolean validateOtp(int userId, String otp);

    // Phương thức vô hiệu hóa OTP
    void deactivateOtp(int userId);
}
