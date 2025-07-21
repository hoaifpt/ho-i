package com.school_medical.school_medical_management_system.repositories.impl;

import com.school_medical.school_medical_management_system.repositories.IEventBatchRepository;
import com.school_medical.school_medical_management_system.repositories.INotificationRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NotificationRepository implements INotificationRepository {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IEventBatchRepository eventBatchRepository;

    @Override
    public void sendBatchNotifications(int batchId, String content, String consentType) {
        String sql = "INSERT INTO Notification (\n" +
                "    content,\n" +
                "    student_id,\n" +
                "    parent_user_id,\n" +
                "    type,\n" +
                "    requires_consent,\n" +
                "    consent_type,\n" +
                "    consent_status,\n" +
                "    batch_id\n" +
                ")\n" +
                "SELECT ?, s.student_id, ps.parent_user_id, ?, TRUE, ?, NULL, ?\n" +
                "FROM EventBatchClass ebc\n" +
                "JOIN StudentClass sc ON sc.class_id = ebc.class_id\n" +
                "JOIN Student s ON s.class_id = sc.class_id\n" +
                "JOIN ParentStudent ps ON ps.student_id = s.student_id\n" +
                "WHERE ebc.batch_id = ?\n" +
                "AND NOT EXISTS (\n" +
                "    SELECT 1 FROM Notification n\n" +
                "    WHERE n.student_id = s.student_id \n" +
                "      AND n.parent_user_id = ps.parent_user_id \n" +
                "      AND n.batch_id = ebc.batch_id\n" +
                ")";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String type = eventBatchRepository.getBatchById(batchId).getBatchType();
            stmt.setString(1, content);        // content
            stmt.setString(2, type);           // type
            stmt.setString(3, consentType);    // consent_type
            stmt.setInt(4, batchId);           // batch_id in insert
            stmt.setInt(5, batchId);           // WHERE batch_id

            int rows = stmt.executeUpdate();
            System.out.println("Inserted " + rows + " notifications for batch " + batchId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while sending notifications", e);
        }
    }

    @Override
    public void updateConsentStatus(Long notificationId, Boolean consentStatus) {
        String sql = "UPDATE Notification SET consent_status = ?, confirmed = TRUE WHERE notification_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, consentStatus);
            stmt.setLong(2, notificationId);

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error updating consent status", e);
        }
    }

    @Override
    public List<Notification> getNotificationsByParentId(Integer parentUserId) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM Notification WHERE parent_user_id = ? ORDER BY date_sent DESC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, parentUserId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Notification notification = new Notification();
                notification.setNotificationId(rs.getLong("notification_id"));
                notification.setContent(rs.getString("content"));
                notification.setDateSent(rs.getTimestamp("date_sent"));
                notification.setConfirmed(rs.getBoolean("confirmed"));
                notification.setType(rs.getString("type"));
                notification.setStudentId(rs.getLong("student_id"));
                notification.setParentUserId(rs.getLong("parent_user_id"));
                notification.setConsentStatus(rs.getObject("consent_status") != null ? rs.getBoolean("consent_status") : null);

                notifications.add(notification);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving notifications", e);
        }

        return notifications;
    }

    // Thêm phương thức này để lấy tất cả notification
    @Override
    public List<Notification> getAllNotifications() {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM Notification ORDER BY date_sent DESC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Notification notification = new Notification();
                notification.setNotificationId(rs.getLong("notification_id"));
                notification.setContent(rs.getString("content"));
                notification.setDateSent(rs.getTimestamp("date_sent"));
                notification.setConfirmed(rs.getBoolean("confirmed"));
                notification.setType(rs.getString("type"));
                notification.setStudentId(rs.getLong("student_id"));
                notification.setParentUserId(rs.getLong("parent_user_id"));
                notification.setConsentStatus(rs.getObject("consent_status") != null ? rs.getBoolean("consent_status") : null);

                notifications.add(notification);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all notifications", e);
        }

        return notifications;
    }

    public void saveNotification(Notification notification) {
        String sql = "INSERT INTO notification (content, date_sent, confirmed, type, student_id, parent_user_id, consent_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                notification.getContent(),
                notification.getDateSent(),
                notification.getConfirmed(),
                notification.getType(),
                notification.getStudentId(),
                notification.getParentUserId(),
                notification.getConsentStatus());
    }
}
