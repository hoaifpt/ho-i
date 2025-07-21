package com.school_medical.school_medical_management_system.services.impl;

import com.school_medical.school_medical_management_system.repositories.IStudentClassRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Studentclass;
import com.school_medical.school_medical_management_system.services.IStudentClassService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentClassServiceImpl implements IStudentClassService {

    private final IStudentClassRepository classRepository;

    public StudentClassServiceImpl(IStudentClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Override
    public List<Studentclass> getAllClasses() {
        return classRepository.getAllClasses();
    }
}
