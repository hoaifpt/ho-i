package com.school_medical.school_medical_management_system.repositories;

import com.school_medical.school_medical_management_system.models.ApprovalRequest;
import com.school_medical.school_medical_management_system.repositories.entites.MedicalEvent;
import java.util.List;

public interface IMedicalEventRepository {
    List<MedicalEvent> getAllEvents();
    void createEvent(MedicalEvent event);
    void updateEvent(Long id, MedicalEvent event);
    void approveEvent(Long id, ApprovalRequest approvalRequest);
    MedicalEvent getEventById(Long id);  // Phương thức mới để lấy sự kiện y tế theo ID
}
