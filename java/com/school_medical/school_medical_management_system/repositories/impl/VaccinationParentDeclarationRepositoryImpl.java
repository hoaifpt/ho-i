package com.school_medical.school_medical_management_system.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.school_medical.school_medical_management_system.models.VaccinationParentDeclarationDTO;
import com.school_medical.school_medical_management_system.repositories.IVaccinationParentDeclarationRepository;

@Repository
public class VaccinationParentDeclarationRepositoryImpl implements IVaccinationParentDeclarationRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public List<VaccinationParentDeclarationDTO> getAllByStudentId(int studentId) {
        String sql = """
            SELECT vpd.*, s.name AS student_name, p.full_name AS parent_name
            FROM vaccinationparentdeclaration vpd
            JOIN student s ON vpd.student_id = s.student_id
            JOIN parent p ON vpd.parent_id = p.parent_id
            WHERE vpd.student_id = ?
            ORDER BY vpd.declared_date DESC
        """;

        List<VaccinationParentDeclarationDTO> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    VaccinationParentDeclarationDTO dto = new VaccinationParentDeclarationDTO(
                            rs.getInt("id"),
                            rs.getInt("student_id"),
                            rs.getString("student_name"),
                            rs.getInt("parent_id"),
                            rs.getString("parent_name"),
                            rs.getString("vaccine_name"),
                            rs.getDate("declared_date"),
                            rs.getString("notes"),
                            rs.getString("status"),
                            rs.getInt("dose_number"),
                            rs.getString("vaccine_lot"),
                            rs.getBoolean("consent_verified")
                    );
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving vaccination declarations", e);
        }

        return list;
    }

    @Override
    public void save(VaccinationParentDeclarationDTO dto) {
        String sql = """
        INSERT INTO vaccinationparentdeclaration (
            student_id, parent_id, vaccine_name, declared_date,
            notes, status, dose_number, vaccine_lot, consent_verified
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dto.getStudentId());
            ps.setInt(2, dto.getParentId());
            ps.setString(3, dto.getVaccineName());
            ps.setDate(4, dto.getDeclaredDate());
            ps.setString(5, dto.getNotes());
            ps.setString(6, dto.getStatus() != null ? dto.getStatus() : "PENDING");
            ps.setInt(7, dto.getDoseNumber());
            ps.setString(8, dto.getVaccineLot());
            ps.setBoolean(9, dto.isConsentVerified());

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error saving vaccination declaration", e);
        }
    }

    @Override
public void deleteById(int id) {
    String sql = "DELETE FROM vaccinationparentdeclaration WHERE id = ?";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        ps.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException("Error deleting vaccination declaration", e);
    }
}

    @Override
    public void update(VaccinationParentDeclarationDTO dto) {
        String sql = """
        UPDATE vaccinationparentdeclaration
        SET vaccine_name = ?, declared_date = ?, notes = ?, status = ?, dose_number = ?, vaccine_lot = ?, consent_verified = ?
        WHERE id = ?
    """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dto.getVaccineName());
            ps.setDate(2, dto.getDeclaredDate());
            ps.setString(3, dto.getNotes());
            ps.setString(4, dto.getStatus() != null ? dto.getStatus() : "PENDING");
            ps.setInt(5, dto.getDoseNumber());
            ps.setString(6, dto.getVaccineLot());
            ps.setBoolean(7, dto.isConsentVerified());
            ps.setInt(8, dto.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating vaccination declaration", e);
        }
    }



}