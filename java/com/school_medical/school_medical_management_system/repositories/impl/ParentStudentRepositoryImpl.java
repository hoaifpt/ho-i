package com.school_medical.school_medical_management_system.repositories.impl;

import com.school_medical.school_medical_management_system.repositories.IParentStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class ParentStudentRepositoryImpl implements IParentStudentRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public boolean isStudentBelongsToParent(int parentId, int studentId) {
        String sql = "SELECT 1 FROM parentstudent WHERE parent_user_id = ? AND student_id = ? LIMIT 1";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, parentId);
            ps.setInt(2, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true nếu tồn tại, false nếu không
            }

        } catch (Exception e) {
            throw new RuntimeException("Error checking parent-student relationship", e);
        }
    }
}
