package com.school_medical.school_medical_management_system.repositories.entites;

import java.time.LocalDateTime;

public class MedicalAppointment {
    private int appointmentId;
    private int studentId;
    private int nurseId;
    private LocalDateTime appointmentDate;
    private String reason;
    private String status;
    private Integer parentUserId; // ✅ Thêm field

    // Getters và Setters
    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getNurseId() { return nurseId; }
    public void setNurseId(int nurseId) { this.nurseId = nurseId; }

    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getParentUserId() { return parentUserId; }
    public void setParentUserId(Integer parentUserId) { this.parentUserId = parentUserId; }
}
