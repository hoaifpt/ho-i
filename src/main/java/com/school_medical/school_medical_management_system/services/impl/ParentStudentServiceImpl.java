package com.school_medical.school_medical_management_system.services.impl;

import com.school_medical.school_medical_management_system.repositories.IParentStudentRepository;
import com.school_medical.school_medical_management_system.services.IParentStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ParentStudentServiceImpl implements IParentStudentService {

    @Autowired
    private IParentStudentRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean isStudentBelongsToParent(int parentId, int studentId) {
        return repository.isStudentBelongsToParent(parentId, studentId);
    }

    @Override
    public List<Integer> getStudentIdsByParentId(int parentUserId) {
        String sql = "SELECT student_id FROM parentstudent WHERE parent_user_id = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, parentUserId);
    }

   
}
