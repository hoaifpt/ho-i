package com.school_medical.school_medical_management_system.services;

import com.school_medical.school_medical_management_system.models.VaccinationRequest;
import com.school_medical.school_medical_management_system.repositories.entites.Vaccination;
import java.util.List;

public interface IVaccinationService {
    Vaccination getVaccinationById(Integer id);
    List<Vaccination> getAllVaccinations();
    void createVaccination(VaccinationRequest request);
    void approveVaccination(Integer vaccinationId, String approvalStatus);
}
