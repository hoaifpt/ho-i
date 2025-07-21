package com.school_medical.school_medical_management_system.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.school_medical.school_medical_management_system.repositories.IHealthInfoRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Healthinfo;

@Repository
public class HealthInfoRepository implements IHealthInfoRepository {

    private static final Logger logger = LoggerFactory.getLogger(HealthInfoRepository.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public Healthinfo findByStudentId(int studentId) {
        String sql = "SELECT * FROM HealthInfo WHERE student_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Healthinfo info = new Healthinfo();
                    info.setHealthInfoId(rs.getInt("health_info_id"));
                    info.setAllergy(rs.getString("allergy"));
                    info.setChronicDisease(rs.getString("chronic_disease"));
                    info.setVision(rs.getString("vision"));
                    info.setHearing(rs.getString("hearing"));
                    info.setMedicalHistory(rs.getString("medical_history"));
                    info.setHeight(rs.getFloat("height"));
                    info.setWeight(rs.getFloat("weight"));
                    info.setBmi(rs.getFloat("bmi"));
                    info.setStudentId(rs.getInt("student_id"));
                    return info;
                } else {
                    logger.info("Không tìm thấy HealthInfo cho student_id {}", studentId);
                    return null;
                }
            }

        } catch (SQLException e) {
            logger.error("❌ Lỗi khi tìm HealthInfo theo student_id {}: {}", studentId, e.getMessage(), e);
            throw new RuntimeException("Lỗi khi tìm HealthInfo theo student_id", e);
        }
    }

    @Override
    public void saveOrUpdate(int studentId, Healthinfo info) {
        String checkSql = "SELECT COUNT(*) FROM HealthInfo WHERE student_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setInt(1, studentId);
            try (ResultSet rs = checkStmt.executeQuery()) {
                rs.next(); // di chuyển con trỏ

                if (rs.getInt(1) > 0) {
                    // Cập nhật
                    String updateSql = "UPDATE HealthInfo SET allergy=?, chronic_disease=?, vision=?, hearing=?, medical_history=?, height=?, weight=?, bmi=? WHERE student_id=?";
                    try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                        stmt.setString(1, info.getAllergy());
                        stmt.setString(2, info.getChronicDisease());
                        stmt.setString(3, info.getVision());
                        stmt.setString(4, info.getHearing());
                        stmt.setString(5, info.getMedicalHistory());
                        stmt.setFloat(6, info.getHeight());
                        stmt.setFloat(7, info.getWeight());
                        stmt.setFloat(8, info.getBmi());
                        stmt.setInt(9, studentId);
                        stmt.executeUpdate();
                        logger.info("✅ Cập nhật HealthInfo thành công cho student_id {}", studentId);
                    }
                } else {
                    // Thêm mới
                    String insertSql = "INSERT INTO HealthInfo (allergy, chronic_disease, vision, hearing, medical_history, height, weight, bmi, student_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                        stmt.setString(1, info.getAllergy());
                        stmt.setString(2, info.getChronicDisease());
                        stmt.setString(3, info.getVision());
                        stmt.setString(4, info.getHearing());
                        stmt.setString(5, info.getMedicalHistory());
                        stmt.setFloat(6, info.getHeight());
                        stmt.setFloat(7, info.getWeight());
                        stmt.setFloat(8, info.getBmi());
                        stmt.setInt(9, studentId);
                        stmt.executeUpdate();
                        logger.info("✅ Thêm mới HealthInfo thành công cho student_id {}", studentId);
                    }
                }
            }

        } catch (SQLException e) {
            logger.error("❌ Lỗi khi lưu hoặc cập nhật HealthInfo cho student_id {}: {}", studentId, e.getMessage(), e);
            throw new RuntimeException("Lỗi khi lưu/cập nhật HealthInfo", e);
        }
    }
}
