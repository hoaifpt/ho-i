package com.school_medical.school_medical_management_system.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OtpRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Method to save OTP in the database
    public boolean saveOtp(int userId, String otp, long expiryTime) {
        String sql = "INSERT INTO otp_info (user_id, otp, expiry_at) VALUES (?, ?, ?)";
        int rows = jdbcTemplate.update(sql, userId, otp, new java.sql.Timestamp(expiryTime));
        return rows > 0;
    }


}
