package com.school_medical.school_medical_management_system.services.impl;

import com.school_medical.school_medical_management_system.models.MedicalCheckupRequest;
import com.school_medical.school_medical_management_system.repositories.entites.Medicalcheckup;
import com.school_medical.school_medical_management_system.repositories.IMedicalCheckupRepository;
import com.school_medical.school_medical_management_system.services.IMedicalCheckupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalCheckupService implements IMedicalCheckupService {

    @Autowired
    private IMedicalCheckupRepository medicalCheckupRepository;

    @Override
    public Medicalcheckup getCheckupById(Integer id) {
        return medicalCheckupRepository.findById(id);
    }

    @Override
    public List<Medicalcheckup> getAllCheckups() {
        return medicalCheckupRepository.findAll();
    }

    @Override
    public void createCheckup(MedicalCheckupRequest request) {
        Medicalcheckup checkup = new Medicalcheckup();
        checkup.setCheckupDate(request.getCheckupDate());
        checkup.setDescription(request.getDescription());
        checkup.setStatus(request.getStatus());
        checkup.setNeedFollowUp(request.getNeedFollowUp());
        checkup.setStudentId(request.getStudentId());
        checkup.setBatchId(request.getBatchId());

        medicalCheckupRepository.create(checkup);
    }

    @Override
    public void approveCheckup(Integer checkupId, String approvalStatus) {
        medicalCheckupRepository.approveCheckup(checkupId, approvalStatus);
    }

}
