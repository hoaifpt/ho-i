package com.school_medical.school_medical_management_system.repositories.impl;

import com.school_medical.school_medical_management_system.models.ApprovalRequest;
import com.school_medical.school_medical_management_system.repositories.IMedicalEventRepository;
import com.school_medical.school_medical_management_system.repositories.entites.MedicalEvent;
import com.school_medical.school_medical_management_system.services.IAppUserService;
import com.school_medical.school_medical_management_system.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalEventRepository implements IMedicalEventRepository {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private IAppUserService appUserService;

    @Override
    public List<MedicalEvent> getAllEvents() {
        List<MedicalEvent> events = new ArrayList<>();
        String sql = "SELECT * FROM MedicalEvent";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MedicalEvent dto = new MedicalEvent();
                dto.setEventId(rs.getLong("event_id"));
                dto.setEventType(rs.getString("event_type"));
                dto.setEventDate(rs.getDate("event_date").toLocalDate());
                dto.setDescription(rs.getString("description"));
                dto.setStudentId(rs.getLong("student_id"));
                dto.setNurseId(rs.getLong("nurse_id"));
                dto.setStatus(rs.getString("status"));
                dto.setApprovedBy(rs.getObject("approved_by") != null ? rs.getInt("approved_by") : null);
                events.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    @Override
    public MedicalEvent getEventById(Long id) {
        String sql = "SELECT * FROM MedicalEvent WHERE event_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                MedicalEvent dto = new MedicalEvent();
                dto.setEventId(rs.getLong("event_id"));
                dto.setEventType(rs.getString("event_type"));
                dto.setEventDate(rs.getDate("event_date").toLocalDate());
                dto.setDescription(rs.getString("description"));
                dto.setStudentId(rs.getLong("student_id"));
                dto.setNurseId(rs.getLong("nurse_id"));
                dto.setStatus(rs.getString("status"));
                dto.setApprovedBy(rs.getObject("approved_by") != null ? rs.getInt("approved_by") : null);
                return dto;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Nếu không tìm thấy
    }

    @Override
    public void createEvent(MedicalEvent event) {
        String sql = "INSERT INTO MedicalEvent (event_type, event_date, description, student_id, nurse_id, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, event.getEventType());
            ps.setDate(2, Date.valueOf(event.getEventDate()));
            ps.setString(3, event.getDescription());
            ps.setLong(4, event.getStudentId());
            ps.setLong(5, event.getNurseId());
            ps.setString(6, "Pending");
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateEvent(Long id, MedicalEvent event) {
        String sql = "UPDATE MedicalEvent SET event_type = ?, event_date = ?, description = ? WHERE event_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, event.getEventType());
            ps.setDate(2, Date.valueOf(event.getEventDate()));
            ps.setString(3, event.getDescription());
            ps.setLong(4, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void approveEvent(Long id, ApprovalRequest approvalRequest) {
        String sql = "UPDATE MedicalEvent SET status = ?, approved_by = ? WHERE event_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, approvalRequest.getApprovalStatus());
            ps.setLong(2, appUserService.getUserByEmail(AuthUtils.getCurrentUserEmail()).getId());
            ps.setLong(3, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
