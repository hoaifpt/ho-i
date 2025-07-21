package com.school_medical.school_medical_management_system.services;

import com.school_medical.school_medical_management_system.repositories.entites.StudentParent;

import java.util.List;

public interface IStudentParentService {
    List<StudentParent> getStudentInfoByParentId(int parentId);
}
