package com.school_medical.school_medical_management_system.repositories.entites;

import java.sql.Timestamp;

public class OtpInfo {
    private Long id;
    private int userId;
    private String otp;
    private Timestamp generatedAt;
    private Timestamp expiryAt;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Timestamp getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Timestamp generatedAt) {
        this.generatedAt = generatedAt;
    }

    public Timestamp getExpiryAt() {
        return expiryAt;
    }

    public void setExpiryAt(Timestamp expiryAt) {
        this.expiryAt = expiryAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
