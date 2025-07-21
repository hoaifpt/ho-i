package com.school_medical.school_medical_management_system.repositories.impl;

import com.school_medical.school_medical_management_system.models.DashboardSummary;
import com.school_medical.school_medical_management_system.repositories.IDashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class DashboardRepositoryImpl implements IDashboardRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public DashboardSummary getSummary() {
        String sql = "SELECT * FROM dashboard_summary LIMIT 1";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapRowToDashboardSummary(rs));
    }

    private DashboardSummary mapRowToDashboardSummary(ResultSet rs) throws SQLException {
        DashboardSummary dto = new DashboardSummary();
        dto.setReportMonth(rs.getString("report_month"));
        dto.setIncome(rs.getLong("income"));
        dto.setUsersToday(rs.getInt("users_today"));
        dto.setNewCustomers(rs.getInt("new_customers"));
        dto.setNewOrders(rs.getInt("new_orders"));
        dto.setActiveUsers(rs.getInt("active_users"));
        dto.setMedicationSubmissions(rs.getInt("medication_submissions"));
        dto.setTotalUsers(rs.getInt("total_users"));
        dto.setReportTitle(rs.getString("report_title"));
        return dto;
    }
}
