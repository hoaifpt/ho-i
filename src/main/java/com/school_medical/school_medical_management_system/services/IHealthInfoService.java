package com.school_medical.school_medical_management_system.services;

import com.school_medical.school_medical_management_system.repositories.entites.Healthinfo;

public interface IHealthInfoService {
    public Healthinfo getHealthInfoByStudentId(int studentId);
    public void saveOrUpdate(int studentId, Healthinfo info);
}
