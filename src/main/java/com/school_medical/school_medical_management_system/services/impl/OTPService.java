package com.school_medical.school_medical_management_system.services.impl;

import com.school_medical.school_medical_management_system.repositories.impl.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class OTPService{

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JavaMailSender javaMailSender;

    public String generateOtp(String email) {
        String otp = generateRandomOtp();

        // Query to check if the email exists
        String selectQuery = "SELECT user_id FROM appuser WHERE email = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(selectQuery)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Email found, retrieve user_id
                int userId = rs.getInt("user_id");

                // Save OTP to the database
                String insertQuery = "INSERT INTO otp_info (user_id, otp, expiry_at) VALUES (?, ?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    Timestamp expiryTime = getOtpExpiryTime();
                    insertStmt.setInt(1, userId);
                    insertStmt.setString(2, otp);
                    insertStmt.setTimestamp(3, expiryTime);
                    insertStmt.executeUpdate();

                    sendOtpEmail(email, otp);
                    return "OTP sent to your email!";
                }
            } else {
                return "User with provided email not found!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error while saving OTP!";
        }
    }

    private String generateRandomOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);  // 6-digit OTP
        return String.valueOf(otp);
    }

    private Timestamp getOtpExpiryTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);  // OTP will expire in 10 minutes
        return new Timestamp(calendar.getTimeInMillis());
    }

    private void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP for password reset is: " + otp);
        javaMailSender.send(message);
    }

    // Phương thức để kiểm tra OTP và thay đổi mật khẩu
    public String changePassword(String email, String otp, String newPassword) {
        String checkOtpQuery = "SELECT * FROM otp_info WHERE user_id = (SELECT user_id FROM appuser WHERE email = ?) AND otp = ? AND status = 'ACTIVE' AND expiry_at > NOW()";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(checkOtpQuery)) {
            stmt.setString(1, email);
            stmt.setString(2, otp);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // OTP hợp lệ, cập nhật mật khẩu
                String updatePasswordQuery = "UPDATE appuser SET password = ? WHERE email = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updatePasswordQuery)) {
                    updateStmt.setString(1, newPassword);
                    updateStmt.setString(2, email);
                    updateStmt.executeUpdate();

                    // Cập nhật trạng thái OTP là đã sử dụng
                    String updateOtpStatusQuery = "UPDATE otp_info SET status = 'USED' WHERE id = ?";
                    try (PreparedStatement updateOtpStatusStmt = conn.prepareStatement(updateOtpStatusQuery)) {
                        updateOtpStatusStmt.setInt(1, rs.getInt("id"));
                        updateOtpStatusStmt.executeUpdate();
                    }

                    return "Password changed successfully!";
                }
            } else {
                return "Invalid OTP or OTP expired!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error occurred while changing password!";
        }
    }
}
