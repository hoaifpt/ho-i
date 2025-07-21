package com.school_medical.school_medical_management_system.repositories;

import com.school_medical.school_medical_management_system.repositories.entites.Healthinfo;

public interface IHealthInfoRepository {
    public Healthinfo findByStudentId(int studentId);
    public void saveOrUpdate(int studentId, Healthinfo info);
}
