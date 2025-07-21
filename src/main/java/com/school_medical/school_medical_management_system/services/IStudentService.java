package com.school_medical.school_medical_management_system.services;

import com.school_medical.school_medical_management_system.repositories.entites.Appuser;
import com.school_medical.school_medical_management_system.repositories.entites.Healthinfo;
import com.school_medical.school_medical_management_system.repositories.entites.Student;
import com.school_medical.school_medical_management_system.repositories.entites.VaccinationParentDeclaration;

import java.util.List;
import java.util.Optional;

public interface IStudentService {
    void saveStudentWithHealthInfo(Student student, Healthinfo healthinfo);
    void saveStudentWithHealthInfoAndLinkParent(Student student, Healthinfo healthinfo, int parentUserId, String relationship);  // Đảm bảo khai báo đúng
    List<Student> getAllStudents();
    List<Student> getStudentsByParentId(int parentUserId);
    List<Healthinfo> getHealthInfoByUserId(int userId);
    List<VaccinationParentDeclaration> getVaccinationInfoByUserId(int userId);
    List<Student> getStudentsByParentUserId(int parentUserId);
    Optional<Appuser> getParentEmailByStudentId(int studentId);
}
