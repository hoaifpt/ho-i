package com.school_medical.school_medical_management_system.api;

import com.school_medical.school_medical_management_system.repositories.entites.Studentclass;
import com.school_medical.school_medical_management_system.services.IStudentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class StudentClassController {

    @Autowired
    private IStudentClassService studentClassService;

    @GetMapping
    public List<Studentclass> getAllClasses() {
        return studentClassService.getAllClasses();
    }
}
