package com.school_medical.school_medical_management_system.services.impl;

import com.school_medical.school_medical_management_system.repositories.IMedicationsubmissionRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Medicationsubmission;
import com.school_medical.school_medical_management_system.services.IMedicationsubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationsubmissionService implements IMedicationsubmissionService {

    @Autowired
    private IMedicationsubmissionRepository repository;

    @Override
    public void save(Medicationsubmission submission) {
        repository.save(submission);
    }

    @Override
    public List<Medicationsubmission> findByParentId(Integer parentId) {
        return repository.findByParentId(parentId);
    }

    @Override
    public Medicationsubmission findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public void approveSubmission(Integer medicationId, Long approvedBy, String approvalStatus) {
        repository.approveSubmission(medicationId, approvedBy, approvalStatus);
    }
}

