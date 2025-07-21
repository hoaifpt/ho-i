package com.school_medical.school_medical_management_system.services;

import com.school_medical.school_medical_management_system.repositories.entites.MedicalAppointment;
import java.util.List;

public interface IMedicalAppointmentService {
    void createAppointment(MedicalAppointment appointment);
    List<MedicalAppointment> getAppointmentsByStudentId(int studentId);
    void approveAppointment(int appointmentId, String status);
    List<MedicalAppointment> getAllAppointments();
    void updateAppointment(MedicalAppointment appointment);  // Thêm phương thức cập nhật
    void deleteAppointment(int appointmentId);  // Thêm phương thức xóa
}
