package com.school_medical.school_medical_management_system.services.impl;

import com.school_medical.school_medical_management_system.repositories.IMedicalAppointmentRepository;
import com.school_medical.school_medical_management_system.repositories.entites.MedicalAppointment;
import com.school_medical.school_medical_management_system.services.IMedicalAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalAppointmentServiceImpl implements IMedicalAppointmentService {

    @Autowired
    private IMedicalAppointmentRepository repository;

    @Override
    public void createAppointment(MedicalAppointment appointment) {
        repository.createAppointment(appointment);
    }
    @Override
    public List<MedicalAppointment> getAppointmentsByStudentId(int studentId) {
        return repository.getAppointmentsByStudentId(studentId);
    }

    @Override
    public void approveAppointment(int appointmentId, String status) {
        repository.approveAppointment(appointmentId, status);
    }

    @Override
    public List<MedicalAppointment> getAllAppointments() {
        return repository.getAllAppointments();
    }

    @Override
    public void updateAppointment(MedicalAppointment appointment) {
        // Implement your update logic here
        // For example, you can update the appointment status or other details in the repository
    }

    @Override
    public void deleteAppointment(int appointmentId) {
        repository.deleteAppointment(appointmentId);
    }
}
