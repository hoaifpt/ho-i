package com.school_medical.school_medical_management_system.services.impl;

import com.school_medical.school_medical_management_system.repositories.IStudentRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Appuser;
import com.school_medical.school_medical_management_system.repositories.entites.Healthinfo;
import com.school_medical.school_medical_management_system.repositories.entites.Student;
import com.school_medical.school_medical_management_system.repositories.entites.VaccinationParentDeclaration;
import com.school_medical.school_medical_management_system.services.IStudentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements IStudentService {

    private final IStudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(IStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public void saveStudentWithHealthInfo(Student student, Healthinfo healthinfo) {
        int studentId = studentRepository.saveStudent(student);
        healthinfo.setStudentId(studentId);
        studentRepository.saveHealthInfo(healthinfo);
    }

    @Override
    @Transactional
    public void saveStudentWithHealthInfoAndLinkParent(Student student, Healthinfo healthinfo, int parentUserId, String relationship) {
        // Lưu học sinh
        int studentId = studentRepository.saveStudent(student);

        // Lưu thông tin sức khỏe của học sinh
        healthinfo.setStudentId(studentId);
        studentRepository.saveHealthInfo(healthinfo);

        // Lưu mối quan hệ giữa phụ huynh và học sinh (cha/mẹ)
        studentRepository.saveParentStudent(parentUserId, studentId, relationship);  // Truyền relationship vào
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.getAllStudents();  // Lấy tất cả học sinh từ repository
    }

    @Override
    public List<Student> getStudentsByParentId(int parentUserId) {
        return studentRepository.getStudentsByParentId(parentUserId);
    }

    @Override
    public List<Healthinfo> getHealthInfoByUserId(int userId) {
        return studentRepository.getHealthInfoByUserId(userId);
    }

    @Override
    public List<VaccinationParentDeclaration> getVaccinationInfoByUserId(int userId) {
        return studentRepository.getVaccinationInfoByUserId(userId);
    }

    @Override
    public List<Student> getStudentsByParentUserId(int parentUserId) {
        return studentRepository.getStudentsByParentUserId(parentUserId);
    }

    @Override
    public Optional<Appuser> getParentEmailByStudentId(int studentId) {
        return studentRepository.findParentEmailByStudentId(studentId);
    }
}
