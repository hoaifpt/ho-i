package com.school_medical.school_medical_management_system.repositories;

import com.school_medical.school_medical_management_system.repositories.entites.StudentParent;

import java.util.List;

public interface IStudentParent {
    List<StudentParent> findByParentId(int parentId);
}
