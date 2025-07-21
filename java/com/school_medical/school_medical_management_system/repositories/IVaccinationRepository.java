package com.school_medical.school_medical_management_system.repositories;

import com.school_medical.school_medical_management_system.repositories.entites.Vaccination;
import java.util.List;

public interface IVaccinationRepository {
    Vaccination findById(Integer vaccinationId);
    List<Vaccination> findAll();
    void create(Vaccination vaccination);
    void approveVaccination(Integer vaccinationId, String approvalStatus);
}
