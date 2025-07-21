package com.school_medical.school_medical_management_system.models;

public class ApproveAppointmentRequest {
    private int appointmentId;
    private String status;  // "Approved" hoặc "Rejected"

    // Getters và Setters
    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
