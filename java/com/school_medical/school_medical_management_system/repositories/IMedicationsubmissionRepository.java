package com.school_medical.school_medical_management_system.repositories;

import com.school_medical.school_medical_management_system.repositories.entites.Medicationsubmission;

import java.util.List;

public interface IMedicationsubmissionRepository {
    void save(Medicationsubmission submission);
    List<Medicationsubmission> findByParentId(Integer parentId);
    Medicationsubmission findById(Integer id);
    void approveSubmission(Integer medicationId, Long approvedBy, String approvalStatus);
}
