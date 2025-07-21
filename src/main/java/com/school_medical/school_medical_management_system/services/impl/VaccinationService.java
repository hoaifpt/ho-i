package com.school_medical.school_medical_management_system.services.impl;

import com.school_medical.school_medical_management_system.models.VaccinationRequest;
import com.school_medical.school_medical_management_system.repositories.entites.Vaccination;
import com.school_medical.school_medical_management_system.repositories.IVaccinationRepository;
import com.school_medical.school_medical_management_system.services.IVaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccinationService implements IVaccinationService {

    @Autowired
    private IVaccinationRepository vaccinationRepository;

    @Override
    public Vaccination getVaccinationById(Integer id) {
        return vaccinationRepository.findById(id);
    }

    @Override
    public List<Vaccination> getAllVaccinations() {
        return vaccinationRepository.findAll();
    }

    @Override
    public void createVaccination(VaccinationRequest request) {
        Vaccination vaccination = new Vaccination();
        vaccination.setVaccineName(request.getVaccineName());
        vaccination.setVaccinationDate(request.getVaccinationDate());
        vaccination.setStatus(request.getStatus());
        vaccination.setConfirmed(request.getConfirmed());
        vaccination.setStudentId(request.getStudentId());
        vaccination.setDeclaredByParent(request.getDeclaredByParent());
        vaccination.setDeclaredDate(request.getDeclaredDate());
        vaccination.setNotes(request.getNotes());
        vaccination.setBatchId(request.getBatchId());

        vaccinationRepository.create(vaccination);
    }

    @Override
    public void approveVaccination(Integer vaccinationId, String approvalStatus) {
        vaccinationRepository.approveVaccination(vaccinationId, approvalStatus);
    }
}
