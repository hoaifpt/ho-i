package com.school_medical.school_medical_management_system.repositories.impl;

import com.school_medical.school_medical_management_system.models.ApprovalRequest;
import com.school_medical.school_medical_management_system.repositories.IEventBatchRepository;
import com.school_medical.school_medical_management_system.repositories.entites.EventBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EventBatchRepository implements IEventBatchRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public void createBatch(EventBatch batch) {
        String sql = "INSERT INTO EventBatch (batch_type, title, description, event_date, status, created_by, end_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, batch.getBatchType());
            ps.setString(2, batch.getTitle());
            ps.setString(3, batch.getDescription());
            ps.setDate(4, Date.valueOf(batch.getEventDate()));
            ps.setString(5, batch.getStatus());
            ps.setLong(6, batch.getCreatedBy());
            ps.setDate(7, batch.getEndDate() != null ? Date.valueOf(batch.getEndDate()) : null);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating event batch", e);
        }
    }

    @Override
    public void approveBatch(Integer batchId) {
        String sql = "UPDATE EventBatch SET status = 'Approved' WHERE batch_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, batchId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error approving event batch", e);
        }
    }

    @Override
    public List<EventBatch> getAllBatches() {
        List<EventBatch> batches = new ArrayList<>();
        String sql = "SELECT * FROM EventBatch WHERE status != 'Deleted'\n" +
                "ORDER BY event_date DESC";  // Lọc bỏ những batch có trạng thái 'Deleted'

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EventBatch batch = new EventBatch();
                batch.setBatchId(rs.getLong("batch_id"));
                batch.setBatchType(rs.getString("batch_type"));
                batch.setTitle(rs.getString("title"));
                batch.setDescription(rs.getString("description"));
                batch.setEventDate(rs.getDate("event_date").toLocalDate());
                batch.setStatus(rs.getString("status"));
                batch.setCreatedBy(rs.getLong("created_by"));
                batches.add(batch);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving event batches", e);
        }

        return batches;
    }

    @Override
    public EventBatch getBatchById(Integer batchId) {
        String sql = "SELECT * FROM EventBatch WHERE batch_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, batchId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    EventBatch batch = new EventBatch();
                    batch.setBatchId(rs.getLong("batch_id"));
                    batch.setBatchType(rs.getString("batch_type"));
                    batch.setTitle(rs.getString("title"));
                    batch.setDescription(rs.getString("description"));
                    batch.setEventDate(rs.getDate("event_date").toLocalDate());
                    batch.setStatus(rs.getString("status"));
                    batch.setCreatedBy(rs.getLong("created_by"));
                    batch.setEndDate(rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null);
                    return batch;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving event batch by ID", e);
        }
        return null;
    }

    @Override
    public List<EventBatch> findTop3UpcomingEvents() {
        List<EventBatch> events = new ArrayList<>();
        String sql = "SELECT * FROM eventbatch WHERE event_date >= CURRENT_DATE() ORDER BY event_date ASC LIMIT 3";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                EventBatch event = new EventBatch();
                event.setBatchId(rs.getLong("batch_id"));
                event.setBatchType(rs.getString("batch_type"));
                event.setTitle(rs.getString("title"));
                event.setDescription(rs.getString("description"));
                event.setEventDate(rs.getDate("event_date").toLocalDate());
                event.setStatus(rs.getString("status"));
                event.setCreatedBy(rs.getLong("created_by"));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public void resendBatch(Integer batchId) {
        String sql = "UPDATE EventBatch SET status = 'Repending' WHERE batch_id = ? AND status = 'Approved'";  // Đảm bảo chỉ cập nhật những batch có trạng thái là 'Approved'
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, batchId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error changing batch status to Repending", e);
        }
    }

    @Override
    public void deleteBatch(Integer batchId) {
        String sql = "UPDATE EventBatch SET status = 'Deleted' WHERE batch_id = ? AND status != 'Deleted'";  // Cập nhật trạng thái là 'Deleted'
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, batchId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error changing batch status to Deleted", e);
        }
    }


}