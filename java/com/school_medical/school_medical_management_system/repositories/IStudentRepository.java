package com.school_medical.school_medical_management_system.repositories;

import com.school_medical.school_medical_management_system.repositories.entites.Appuser;
import com.school_medical.school_medical_management_system.repositories.entites.Student;
import com.school_medical.school_medical_management_system.repositories.entites.Healthinfo;
import com.school_medical.school_medical_management_system.repositories.entites.VaccinationParentDeclaration;

import java.util.List;
import java.util.Optional;

public interface IStudentRepository {
    int saveStudent(Student student);  // Lưu học sinh
    int saveHealthInfo(Healthinfo healthInfo);  // Lưu thông tin sức khỏe
    void saveParentStudent(int parentUserId, int studentId, String relationship);  // Lưu mối quan hệ cha/mẹ
    List<Student> getAllStudents();  // Lấy tất cả học sinh
    List<Student> getStudentsByParentId(int parentUserId);
    List<Healthinfo> getHealthInfoByUserId(int userId);
    List<VaccinationParentDeclaration> getVaccinationInfoByUserId(int userId);
    List<Student> getStudentsByParentUserId(int parentUserId);
    Optional<Appuser> findParentEmailByStudentId(int studentId);
}
