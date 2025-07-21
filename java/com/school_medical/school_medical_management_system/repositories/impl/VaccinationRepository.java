package com.school_medical.school_medical_management_system.repositories.impl;

import com.school_medical.school_medical_management_system.repositories.IVaccinationRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Vaccination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VaccinationRepository implements IVaccinationRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public Vaccination findById(Integer vaccinationId) {
        String sql = "SELECT * FROM Vaccination WHERE vaccination_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, vaccinationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Vaccination vac = new Vaccination();
                    vac.setVaccinationId(rs.getInt("vaccination_id"));
                    vac.setVaccineName(rs.getString("vaccine_name"));
                    vac.setVaccinationDate(rs.getDate("vaccination_date"));
                    vac.setStatus(rs.getString("status"));
                    vac.setConfirmed(rs.getBoolean("confirmed"));
                    vac.setStudentId(rs.getInt("student_id"));
                    vac.setDeclaredByParent(rs.getBoolean("declared_by_parent"));
                    vac.setDeclaredDate(rs.getDate("declared_date"));
                    vac.setNotes(rs.getString("notes"));

                    Object batchIdObj = rs.getObject("batch_id");
                    if (batchIdObj != null) {
                        vac.setBatchId(rs.getInt("batch_id"));
                    } else {
                        vac.setBatchId(null);
                    }
                    return vac;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error finding vaccination by ID: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Vaccination> findAll() {
        List<Vaccination> result = new ArrayList<>();
        String sql = "SELECT * FROM Vaccination";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vaccination vac = new Vaccination();
                vac.setVaccinationId(rs.getInt("vaccination_id"));
                vac.setVaccineName(rs.getString("vaccine_name"));
                vac.setVaccinationDate(rs.getDate("vaccination_date"));
                vac.setStatus(rs.getString("status"));
                vac.setConfirmed(rs.getBoolean("confirmed"));
                vac.setStudentId(rs.getInt("student_id"));
                vac.setDeclaredByParent(rs.getBoolean("declared_by_parent"));
                vac.setDeclaredDate(rs.getDate("declared_date"));
                vac.setNotes(rs.getString("notes"));

                Object batchIdObj = rs.getObject("batch_id");
                if (batchIdObj != null) {
                    vac.setBatchId(rs.getInt("batch_id"));
                } else {
                    vac.setBatchId(null);
                }

                result.add(vac);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all vaccinations: " + e.getMessage(), e);
        }
        return result;
    }

    @Override
    public void create(Vaccination vaccination) {
        String sql ="INSERT INTO Vaccination (\n" +
                "                vaccine_name, vaccination_date, status, confirmed, student_id,\n" +
                "                declared_by_parent, declared_date, notes, batch_id\n" +
                "            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Vaccine Name
            stmt.setString(1, vaccination.getVaccineName());

            // Vaccination Date
            if (vaccination.getVaccinationDate() != null) {
                stmt.setDate(2, new java.sql.Date(vaccination.getVaccinationDate().getTime()));
            } else {
                stmt.setNull(2, Types.DATE);
            }

            // Status
            if (vaccination.getStatus() != null) {
                stmt.setString(3, vaccination.getStatus());
            } else {
                stmt.setString(3, "Pending");
            }

            // Confirmed
            if (vaccination.getConfirmed() != null) {
                stmt.setBoolean(4, vaccination.getConfirmed());
            } else {
                stmt.setBoolean(4, false);
            }

            // Student ID
            stmt.setInt(5, vaccination.getStudentId());

            // Declared By Parent
            if (vaccination.getDeclaredByParent() != null) {
                stmt.setBoolean(6, vaccination.getDeclaredByParent());
            } else {
                stmt.setBoolean(6, false);
            }

            // Declared Date
            if (vaccination.getDeclaredDate() != null) {
                stmt.setDate(7, new java.sql.Date(vaccination.getDeclaredDate().getTime()));
            } else {
                stmt.setNull(7, Types.DATE);
            }

            // Notes
            stmt.setString(8, vaccination.getNotes());

            // Batch ID
            if (vaccination.getBatchId() != null) {
                stmt.setInt(9, vaccination.getBatchId());
            } else {
                stmt.setNull(9, Types.INTEGER);
            }

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error while creating vaccination: " + e.getMessage(), e);
        }
    }

    @Override
    public void approveVaccination(Integer vaccinationId, String approvalStatus) {
        String sql = "UPDATE Vaccination SET status = ? WHERE vaccination_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, approvalStatus);
            stmt.setInt(2, vaccinationId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error approving vaccination: " + e.getMessage(), e);
        }
    }
}
