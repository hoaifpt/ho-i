package com.school_medical.school_medical_management_system.api;

import com.school_medical.school_medical_management_system.models.OtpRequest;
import com.school_medical.school_medical_management_system.repositories.IUserRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Appuser;
import com.school_medical.school_medical_management_system.services.IAppUserService;
import com.school_medical.school_medical_management_system.services.IOTPService;
import com.school_medical.school_medical_management_system.services.impl.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/otp")
public class OtpController {
    @Autowired
    private OTPService otpService;

    // Endpoint gửi OTP
    @PostMapping("/generate")
    public ResponseEntity<String> generateOtp(@RequestParam String email) {
        String responseMessage = otpService.generateOtp(email);
        return ResponseEntity.ok(responseMessage);
    }

    // Endpoint thay đổi mật khẩu sau khi xác thực OTP
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestParam String email,
            @RequestParam String otp,
            @RequestParam String newPassword) {
        String responseMessage = otpService.changePassword(email, otp, newPassword);
        return ResponseEntity.ok(responseMessage);
    }
}