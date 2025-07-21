package com.school_medical.school_medical_management_system.repositories.impl;

import com.school_medical.school_medical_management_system.repositories.IMedicalSupplyRepository;
import com.school_medical.school_medical_management_system.repositories.entites.MedicalSupply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalSupplyRepository implements IMedicalSupplyRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public void createSupply(MedicalSupply supply) {
        String sql = "INSERT INTO medicalsupply (name, quantity, description, last_checked_date, status, expiration_date, unit) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, supply.getName());
            stmt.setInt(2, supply.getQuantity());
            stmt.setString(3, supply.getDescription());
            stmt.setDate(4, supply.getLastCheckedDate() != null ? new java.sql.Date(supply.getLastCheckedDate().getTime()) : null);
            stmt.setInt(5, 1); // Default status
            stmt.setDate(6, supply.getExpirationDate() != null ? new java.sql.Date(supply.getExpirationDate().getTime()) : null);
            stmt.setString(7, supply.getUnit());  // Đảm bảo thêm unit vào khi tạo

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating supply", e);
        }
    }

    @Override
    public List<MedicalSupply> getAllSupplies() {
        List<MedicalSupply> supplies = new ArrayList<>();
        String sql = "SELECT * FROM medicalsupply WHERE status = 1"; // Only active supplies
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MedicalSupply supply = new MedicalSupply();
                supply.setSupplyId(rs.getInt("supply_id"));
                supply.setName(rs.getString("name"));
                supply.setQuantity(rs.getInt("quantity"));
                supply.setDescription(rs.getString("description"));
                supply.setLastCheckedDate(rs.getDate("last_checked_date"));
                supply.setStatus(rs.getInt("status"));
                supply.setExpirationDate(rs.getDate("expiration_date"));
                supply.setUnit(rs.getString("unit"));  // Đọc unit
                supplies.add(supply);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving supplies", e);
        }
        return supplies;
    }

    @Override
    public MedicalSupply findById(Integer id) {
        String sql = "SELECT * FROM medicalsupply WHERE supply_id = ? AND status = 1"; // Only active supplies
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    MedicalSupply supply = new MedicalSupply();
                    supply.setSupplyId(rs.getInt("supply_id"));
                    supply.setName(rs.getString("name"));
                    supply.setQuantity(rs.getInt("quantity"));
                    supply.setDescription(rs.getString("description"));
                    supply.setLastCheckedDate(rs.getDate("last_checked_date"));
                    supply.setStatus(rs.getInt("status"));
                    supply.setExpirationDate(rs.getDate("expiration_date"));
                    supply.setUnit(rs.getString("unit"));  // Đọc unit
                    return supply;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding supply by ID", e);
        }
        return null;
    }

    @Override
    public void updateSupply(MedicalSupply supply) {
        String sql = "UPDATE medicalsupply SET name = ?, quantity = ?, description = ?, last_checked_date = ?, status = ?, expiration_date = ?, unit = ? WHERE supply_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, supply.getName());
            stmt.setInt(2, supply.getQuantity());
            stmt.setString(3, supply.getDescription());
            stmt.setDate(4, supply.getLastCheckedDate() != null ? new java.sql.Date(supply.getLastCheckedDate().getTime()) : null);
            stmt.setInt(5, supply.getStatus() != null ? supply.getStatus() : 1);  // Default status if null
            stmt.setDate(6, supply.getExpirationDate() != null ? new java.sql.Date(supply.getExpirationDate().getTime()) : null);
            stmt.setString(7, supply.getUnit());  // Cập nhật unit
            stmt.setInt(8, supply.getSupplyId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating supply", e);
        }
    }

    @Override
    public void deleteSupply(Integer id) {
        String sql = "UPDATE medicalsupply SET status = 0 WHERE supply_id = ?"; // Soft delete
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error soft-deleting supply", e);
        }
    }
}
