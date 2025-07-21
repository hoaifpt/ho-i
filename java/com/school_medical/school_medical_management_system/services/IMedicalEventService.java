package com.school_medical.school_medical_management_system.services;

import com.school_medical.school_medical_management_system.models.ApprovalRequest;
import com.school_medical.school_medical_management_system.repositories.entites.MedicalEvent;

import java.util.List;

public interface IMedicalEventService {
    List<MedicalEvent> getAllEvents();
    MedicalEvent createEvent(MedicalEvent eventDTO);
    MedicalEvent updateEvent(Long id, MedicalEvent eventDTO);
    MedicalEvent approveEvent(Long id, ApprovalRequest approvalRequest);
    MedicalEvent getEventById(Long id); // Phương thức mới để lấy sự kiện y tế theo ID
}
