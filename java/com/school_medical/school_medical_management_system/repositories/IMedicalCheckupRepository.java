package com.school_medical.school_medical_management_system.repositories;

import com.school_medical.school_medical_management_system.repositories.entites.Medicalcheckup;
import java.util.List;

public interface IMedicalCheckupRepository {
    Medicalcheckup findById(Integer checkupId);
    List<Medicalcheckup> findAll();
    void create(Medicalcheckup checkup);
    void approveCheckup(Integer checkupId, String approvalStatus);

}
