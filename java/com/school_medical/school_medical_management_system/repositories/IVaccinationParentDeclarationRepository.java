package com.school_medical.school_medical_management_system.repositories;

import java.util.List;

import com.school_medical.school_medical_management_system.models.VaccinationParentDeclarationDTO;

public interface IVaccinationParentDeclarationRepository {
    List<VaccinationParentDeclarationDTO> getAllByStudentId(int studentId);
    void save(VaccinationParentDeclarationDTO dto);
    void deleteById(int id);
    void update(VaccinationParentDeclarationDTO dto);
}