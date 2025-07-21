package com.school_medical.school_medical_management_system.repositories.impl;

import com.school_medical.school_medical_management_system.repositories.IMedicalEventSupplyRepository;
import com.school_medical.school_medical_management_system.repositories.entites.MedicalEventSupply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalEventSupplyRepository implements IMedicalEventSupplyRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public void addSupplyToEvent(MedicalEventSupply eventSupply) {
        String sql = "INSERT INTO medicaleventsupply (event_id, supply_id, quantity_used) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventSupply.getEventId());
            stmt.setInt(2, eventSupply.getSupplyId());
            stmt.setInt(3, eventSupply.getQuantityUsed());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding supply to event", e);
        }
    }

    @Override
    public List<MedicalEventSupply> getSuppliesByEventId(Integer eventId) {
        List<MedicalEventSupply> supplies = new ArrayList<>();
        String sql = "SELECT * FROM medicaleventsupply WHERE event_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MedicalEventSupply supply = new MedicalEventSupply();
                    supply.setId(rs.getInt("id"));
                    supply.setEventId(rs.getInt("event_id"));
                    supply.setSupplyId(rs.getInt("supply_id"));
                    supply.setQuantityUsed(rs.getInt("quantity_used"));
                    supplies.add(supply);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving supplies by event", e);
        }
        return supplies;
    }

    @Override
    public void updateQuantityUsed(Integer id, Integer newQuantity) {
        String sql = "UPDATE medicaleventsupply SET quantity_used = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newQuantity);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating quantity used", e);
        }
    }

    @Override
    public MedicalEventSupply findById(Integer eventSupplyId) {
        String sql = "SELECT * FROM medicaleventsupply WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventSupplyId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    MedicalEventSupply supply = new MedicalEventSupply();
                    supply.setId(rs.getInt("id"));
                    supply.setEventId(rs.getInt("event_id"));
                    supply.setSupplyId(rs.getInt("supply_id"));
                    supply.setQuantityUsed(rs.getInt("quantity_used"));
                    return supply;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding event supply by ID", e);
        }
        return null;
    }

    @Override
    public List<MedicalEventSupply> getAllEventSupplies() {
        List<MedicalEventSupply> supplies = new ArrayList<>();
        String sql = "SELECT * FROM medicaleventsupply";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MedicalEventSupply supply = new MedicalEventSupply();
                supply.setId(rs.getInt("id"));
                supply.setEventId(rs.getInt("event_id"));
                supply.setSupplyId(rs.getInt("supply_id"));
                supply.setQuantityUsed(rs.getInt("quantity_used"));
                supplies.add(supply);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all event supplies", e);
        }
        return supplies;
    }

}

