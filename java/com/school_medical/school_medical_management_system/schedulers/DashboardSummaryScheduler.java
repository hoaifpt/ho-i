package com.school_medical.school_medical_management_system.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class DashboardSummaryScheduler {

    @Autowired
    private DataSource dataSource;

    // Tự động chạy vào 00:00 ngày 1 hàng tháng
    @Scheduled(cron = "0 0 0 1 * *")
    public void recreateView() {
        String sql = """
            CREATE OR REPLACE VIEW dashboard_summary AS
            SELECT 
                DATE_FORMAT(NOW(), '%Y-%m') AS report_month,
                IFNULL((SELECT SUM(amount) FROM `order` WHERE MONTH(created_at) = MONTH(NOW()) AND YEAR(created_at) = YEAR(NOW())), 0) AS income,
                0 AS users_today, -- Gán tạm nếu chưa có cột `last_login`
                (SELECT COUNT(*) FROM appuser WHERE MONTH(created_at) = MONTH(NOW()) AND YEAR(created_at) = YEAR(NOW())) AS new_customers,
                (SELECT COUNT(*) FROM `order` WHERE DATE(created_at) = CURDATE()) AS new_orders,
                (SELECT COUNT(*) FROM appuser) AS active_users,
                (SELECT COUNT(*) FROM medicationsubmission WHERE MONTH(start_date) = MONTH(NOW()) AND YEAR(start_date) = YEAR(NOW())) AS medication_submissions,
                (SELECT COUNT(*) FROM appuser) AS total_users,
                CONCAT('Báo cáo tổng hợp sức khỏe học đường tháng ', MONTH(NOW())) AS report_title;
        """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Chạy câu lệnh SQL để tạo lại VIEW
            stmt.executeUpdate();
            System.out.println("Dashboard summary view recreated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
