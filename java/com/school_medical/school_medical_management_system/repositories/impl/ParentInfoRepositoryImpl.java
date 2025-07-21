package com.school_medical.school_medical_management_system.repositories.impl;

import com.school_medical.school_medical_management_system.models.ParentInfoDTO;
import com.school_medical.school_medical_management_system.repositories.IParentInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class ParentInfoRepositoryImpl implements IParentInfoRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public ParentInfoDTO getParentInfoByUserId(int userId) {
        String sql = """
            SELECT p.full_name, p.phone, a.email, a.address
            FROM parent p
            JOIN appuser a ON p.user_id = a.user_id
            WHERE p.user_id = ?
            LIMIT 1
        """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ParentInfoDTO(
                            rs.getString("full_name"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getString("address")
                    );
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error retrieving parent info by userId", e);
        }

        return null;
    }
}
