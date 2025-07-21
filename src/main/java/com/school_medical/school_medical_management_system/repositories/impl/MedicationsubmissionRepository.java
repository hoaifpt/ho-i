package com.school_medical.school_medical_management_system.repositories.impl;

import com.school_medical.school_medical_management_system.repositories.IMedicationsubmissionRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Medicationsubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicationsubmissionRepository implements IMedicationsubmissionRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public void save(Medicationsubmission submission) {
        String sql = "INSERT INTO medicationsubmission " +
                "(medication_name, dosage, frequency, start_date, end_date, status, parent_user_id, student_id, approved_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, submission.getMedicationName());
            ps.setString(2, submission.getDosage());
            ps.setString(3, submission.getFrequency());
            ps.setDate(4, submission.getStartDate() != null ? Date.valueOf(submission.getStartDate()) : null);
            ps.setDate(5, submission.getEndDate() != null ? Date.valueOf(submission.getEndDate()) : null);
            ps.setString(6, submission.getStatus());
            ps.setObject(7, submission.getParentUserId());
            ps.setObject(8, submission.getStudentId());
            ps.setObject(9, submission.getApprovedBy());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error saving medication submission", e);
        }
    }

    @Override
    public List<Medicationsubmission> findByParentId(Integer parentId) {
        String sql = "SELECT * FROM medicationsubmission WHERE parent_user_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, parentId);

            try (ResultSet rs = ps.executeQuery()) {
                List<Medicationsubmission> submissions = new ArrayList<>();
                while (rs.next()) {
                    submissions.add(new Medicationsubmission() {{
                        setMedicationId(rs.getInt("medication_id"));
                        setMedicationName(rs.getString("medication_name"));
                        setDosage(rs.getString("dosage"));
                        setFrequency(rs.getString("frequency"));
                        setStartDate(rs.getDate("start_date") != null ? rs.getDate("start_date").toLocalDate() : null);
                        setEndDate(rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null);
                        setStatus(rs.getString("status"));
                        setParentUserId(rs.getInt("parent_user_id"));
                        setStudentId(rs.getInt("student_id"));
                        setApprovedBy(rs.getObject("approved_by") != null ? rs.getInt("approved_by") : null);
                    }});
                }
                return submissions;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving medication submissions by parent ID", e);
        }
    }


    @Override
    public Medicationsubmission findById(Integer id) {
        String sql = "SELECT * FROM medicationsubmission WHERE medication_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Medicationsubmission submission = new Medicationsubmission();
                    submission.setMedicationId(rs.getInt("medication_id"));
                    submission.setMedicationName(rs.getString("medication_name"));
                    submission.setDosage(rs.getString("dosage"));
                    submission.setFrequency(rs.getString("frequency"));
                    submission.setStartDate(rs.getDate("start_date") != null ? rs.getDate("start_date").toLocalDate() : null);
                    submission.setEndDate(rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null);
                    submission.setStatus(rs.getString("status"));
                    return submission;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving medication submission by ID", e);
        }

        return null;
    }

    @Override
    public void approveSubmission(Integer medicationId, Long approvedBy, String approvalStatus) {
        String sql = "UPDATE medicationsubmission SET approved_by = ?, status = ? WHERE medication_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, approvedBy);
            ps.setString(2, approvalStatus);
            ps.setInt(3, medicationId);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error approving medication submission", e);
        }
    }
}

