package com.school_medical.school_medical_management_system.repositories;

import com.school_medical.school_medical_management_system.repositories.entites.Studentclass;

import java.util.List;

public interface IStudentClassRepository {
    List<Studentclass> getAllClasses(); // ✅ Lấy danh sách tất cả lớp học
}
