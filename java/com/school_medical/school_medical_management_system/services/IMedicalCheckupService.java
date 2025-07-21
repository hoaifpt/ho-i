package com.school_medical.school_medical_management_system.services;

import com.school_medical.school_medical_management_system.models.MedicalCheckupRequest;
import com.school_medical.school_medical_management_system.repositories.entites.Medicalcheckup;
import java.util.List;

public interface IMedicalCheckupService {
    Medicalcheckup getCheckupById(Integer id);
    List<Medicalcheckup> getAllCheckups();
    void createCheckup(MedicalCheckupRequest request);
    void approveCheckup(Integer checkupId, String approvalStatus);
}
