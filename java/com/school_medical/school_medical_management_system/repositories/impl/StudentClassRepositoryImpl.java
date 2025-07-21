package com.school_medical.school_medical_management_system.repositories.impl;

import com.school_medical.school_medical_management_system.repositories.IStudentClassRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Appuser;
import com.school_medical.school_medical_management_system.repositories.entites.Studentclass;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentClassRepositoryImpl implements IStudentClassRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudentClassRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Studentclass> getAllClasses() {
        String sql = "SELECT sc.class_id, sc.class_name, sc.room, " +
                "u.user_id, u.first_name, u.last_name, u.email, u.phone, u.address, r.role_name " +
                "FROM mesch.studentclass sc " +
                "LEFT JOIN mesch.appuser u ON sc.manager_id = u.user_id " +
                "LEFT JOIN mesch.role r ON u.role_id = r.role_id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Studentclass studentClass = new Studentclass();
            studentClass.setId(rs.getInt("class_id"));
            studentClass.setClassName(rs.getString("class_name"));
            studentClass.setRoom(rs.getString("room"));

            Appuser manager = new Appuser();
            manager.setId(rs.getInt("user_id"));
            manager.setFirstName(rs.getString("first_name"));
            manager.setLastName(rs.getString("last_name"));
            manager.setEmail(rs.getString("email"));
            manager.setPhone(rs.getString("phone"));
            manager.setAddress(rs.getString("address"));
            manager.setRoleName(rs.getString("role_name"));
            // ✅ Không gọi setCreatedAt nữa

            studentClass.setManager(manager);

            return studentClass;
        });
    }

}
