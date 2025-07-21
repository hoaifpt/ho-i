package com.school_medical.school_medical_management_system.repositories.impl;

import com.school_medical.school_medical_management_system.repositories.IReportRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Report;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ReportRepositoryImpl implements IReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReportRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addReport(Report report) {
        // Nếu report_date hoặc created_at chưa được thiết lập, hãy thiết lập bằng thời gian hiện tại
        if (report.getReportDate() == null) {
            report.setReportDate(new Timestamp(System.currentTimeMillis())); // Thiết lập thời gian hiện tại cho report_date
        }

        if (report.getCreatedAt() == null) {
            report.setCreatedAt(new Timestamp(System.currentTimeMillis())); // Thiết lập thời gian hiện tại cho created_at
        }

        String sql = "INSERT INTO mesch.report (report_date, description, result_expected, file_attachment, error_type, user_id, status, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, report.getReportDate(), report.getDescription(), report.getResultExpected(),
                report.getFileAttachment(), report.getErrorType(), report.getUserId(), report.getStatus(),
                report.getCreatedAt());
    }


    @Override
    public void updateReport(Report report) {
        String sql = "UPDATE mesch.report SET description = ?, result_expected = ?, file_attachment = ?, " +
                "error_type = ?, user_id = ?, status = ? WHERE report_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, report.getDescription(), report.getResultExpected(),
                report.getFileAttachment(), report.getErrorType(), report.getUserId(),
                report.getStatus(), report.getReportId());

        if (rowsAffected == 0) {
            throw new EmptyResultDataAccessException("No report found with the given ID", 1);
        }
    }


    @Override
    public void deleteReport(int reportId) {
        String sql = "DELETE FROM mesch.report WHERE report_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, reportId);
        if (rowsAffected == 0) {
            throw new EmptyResultDataAccessException("No report found with the given ID", 1);
        }
    }

    @Override
    public Report getReportById(int reportId) {
        String sql = "SELECT * FROM mesch.report WHERE report_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{reportId}, (rs, rowNum) -> {
                Report report = new Report();
                report.setReportId(rs.getInt("report_id"));
                report.setReportDate(rs.getDate("report_date"));
                report.setDescription(rs.getString("description"));
                report.setResultExpected(rs.getString("result_expected"));
                report.setFileAttachment(rs.getString("file_attachment"));
                report.setErrorType(rs.getString("error_type"));
                report.setUserId(rs.getInt("user_id"));
                report.setStatus(rs.getString("status"));
                report.setCreatedAt(rs.getDate("created_at"));
                return report;
            });
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null if no report found
        }
    }

    @Override
    public List<Report> getAllReports() {
        String sql = "SELECT * FROM mesch.report ORDER BY report_date DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Report report = new Report();
            report.setReportId(rs.getInt("report_id"));
            report.setReportDate(rs.getDate("report_date"));
            report.setDescription(rs.getString("description"));
            report.setResultExpected(rs.getString("result_expected"));
            report.setFileAttachment(rs.getString("file_attachment"));
            report.setErrorType(rs.getString("error_type"));
            report.setUserId(rs.getInt("user_id"));
            report.setStatus(rs.getString("status"));
            report.setCreatedAt(rs.getDate("created_at"));
            return report;
        });
    }
}
