package com.school_medical.school_medical_management_system.repositories.impl;

import com.school_medical.school_medical_management_system.repositories.IStudentParent;
import com.school_medical.school_medical_management_system.repositories.entites.StudentParent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentParentRepository implements IStudentParent {

    private final DataSource dataSource;

    @Autowired
    public StudentParentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<StudentParent> findByParentId(int parentId) {
        List<StudentParent> result = new ArrayList<>();
        String sql = """
            SELECT 
                s.name AS student_name,
                sc.class_name,
                CONCAT(teacher.first_name, ' ', teacher.last_name) AS class_teacher,
                hi.height,
                hi.weight,
                s.gender,
                CONCAT(parent.first_name, ' ', parent.last_name) AS parent_name,
                parent.phone AS parent_phone,
                parent.email AS parent_email,
                parent.address AS parent_address
            FROM ParentStudent ps
            JOIN Student s ON ps.student_id = s.student_id
            LEFT JOIN StudentClass sc ON s.class_id = sc.class_id
            LEFT JOIN AppUser teacher ON sc.manager_id = teacher.user_id
            LEFT JOIN HealthInfo hi ON s.student_id = hi.student_id
            JOIN AppUser parent ON ps.parent_user_id = parent.user_id
            WHERE ps.parent_user_id = ?
        """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, parentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                StudentParent dto = new StudentParent();
                dto.setStudentName(rs.getString("student_name"));
                dto.setClassName(rs.getString("class_name"));
                dto.setClassTeacher(rs.getString("class_teacher"));
                dto.setHeight(rs.getFloat("height"));
                dto.setWeight(rs.getFloat("weight"));
                dto.setGender(rs.getString("gender"));
                dto.setParentName(rs.getString("parent_name"));
                dto.setParentPhone(rs.getString("parent_phone"));
                dto.setParentEmail(rs.getString("parent_email"));
                dto.setParentAddress(rs.getString("parent_address"));
                result.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
