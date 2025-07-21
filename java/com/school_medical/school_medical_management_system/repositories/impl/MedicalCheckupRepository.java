package com.school_medical.school_medical_management_system.repositories.impl;

import com.school_medical.school_medical_management_system.repositories.entites.Medicalcheckup;
import com.school_medical.school_medical_management_system.repositories.IMedicalCheckupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalCheckupRepository implements IMedicalCheckupRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public Medicalcheckup findById(Integer checkupId) {
        String sql = "SELECT * FROM MedicalCheckup WHERE checkup_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, checkupId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Medicalcheckup> findAll() {
        List<Medicalcheckup> result = new ArrayList<>();
        String sql = "SELECT * FROM MedicalCheckup";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                result.add(mapResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Medicalcheckup mapResultSet(ResultSet rs) throws SQLException {
        Medicalcheckup checkup = new Medicalcheckup();
        checkup.setCheckupId(rs.getInt("checkup_id"));
        checkup.setCheckupDate(rs.getDate("checkup_date"));
        checkup.setDescription(rs.getString("description"));
        checkup.setStatus(rs.getString("status"));
        checkup.setNeedFollowUp(rs.getBoolean("need_follow_up"));
        checkup.setStudentId(rs.getInt("student_id"));
        checkup.setBatchId(rs.getObject("batch_id") != null ? rs.getInt("batch_id") : null);
        return checkup;
    }

    @Override
    public void create(Medicalcheckup checkup) {
        String sql = "INSERT INTO MedicalCheckup (checkup_date, description, status, need_follow_up, student_id, batch_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(checkup.getCheckupDate().getTime()));
            stmt.setString(2, checkup.getDescription());
            stmt.setString(3, checkup.getStatus());
            stmt.setBoolean(4, checkup.getNeedFollowUp());
            stmt.setInt(5, checkup.getStudentId());

            if (checkup.getBatchId() != null) {
                stmt.setInt(6, checkup.getBatchId());
            } else {
                stmt.setNull(6, java.sql.Types.INTEGER);
            }

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void approveCheckup(Integer checkupId, String approvalStatus) {
        String sql = "UPDATE MedicalCheckup SET status = ? WHERE checkup_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, approvalStatus);
            stmt.setInt(2, checkupId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error approving medical checkup", e);
        }
    }
}
