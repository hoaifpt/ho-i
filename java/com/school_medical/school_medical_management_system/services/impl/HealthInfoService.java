package com.school_medical.school_medical_management_system.services.impl;

import com.school_medical.school_medical_management_system.repositories.entites.Healthinfo;
import com.school_medical.school_medical_management_system.repositories.impl.HealthInfoRepository;
import com.school_medical.school_medical_management_system.services.IHealthInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthInfoService implements IHealthInfoService {

    @Autowired
    private HealthInfoRepository repository;

    public Healthinfo getHealthInfoByStudentId(int studentId) {
        try {
            return repository.findByStudentId(studentId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveOrUpdate(int studentId, Healthinfo info) {
        repository.saveOrUpdate(studentId, info);
    }
}