package com.school_medical.school_medical_management_system.repositories.impl;

import com.school_medical.school_medical_management_system.repositories.IUserRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Appuser;
import com.school_medical.school_medical_management_system.repositories.entites.Parent;
import com.school_medical.school_medical_management_system.repositories.entites.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    @Autowired
    private DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDetails findUserByEmail(String email) {
        String sql = "SELECT a.user_id, a.email, a.password, r.role_name " +
                "FROM appuser a JOIN role r ON a.role_id = r.role_id " +
                "WHERE a.email = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role_name");
                    return User.builder()
                            .username(rs.getString("email"))
                            .password(rs.getString("password"))
                            .roles(role.toUpperCase())
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Appuser getUserByEmail(String email) {
        String sql = "SELECT user_id, first_name, last_name, email, phone, address, appuser.role_id, role_name, created_at " +
                "FROM appuser JOIN role ON appuser.role_id = role.role_id WHERE email = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Appuser user = new Appuser();
                    user.setId(rs.getInt("user_id"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));
                    user.setRoleId(rs.getInt("role_id"));         // ✅ Bổ sung
                    user.setRoleName(rs.getString("role_name"));
                    user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Appuser> findByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM appuser WHERE user_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Appuser user = new Appuser();
                user.setId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime()); // ✅ Mới
                return Optional.of(user);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error querying user by account number", e);
        }
    }

    @Override
    public List<Appuser> getAllNurses() {
        String sql = "SELECT a.user_id, a.first_name, a.last_name, a.email, a.phone, a.address, r.role_name " +
                "FROM appuser a JOIN role r ON a.role_id = r.role_id " +
                "WHERE a.role_id = 3";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Appuser> nurses = new java.util.ArrayList<>();
            while (rs.next()) {
                Appuser user = new Appuser();
                user.setId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setRoleName(rs.getString("role_name"));
                // Không set createdAt vì bạn muốn ẩn
                nurses.add(user);
            }
            return nurses;

        } catch (SQLException e) {
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }

    @Override
    public List<String> getAllUserEmails() {
        String sql = "SELECT email FROM appuser";
        return jdbcTemplate.queryForList(sql, String.class);
    }


    @Override
    public List<Appuser> getAllUsers() {
        String sql = "SELECT user_id, first_name, last_name, email, phone, address, role_name, created_at " +
                "FROM appuser JOIN role ON appuser.role_id = role.role_id";

        List<Appuser> users = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Appuser user = new Appuser();
                user.setId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setRoleName(rs.getString("role_name"));
                user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Appuser saveUser(Appuser user) {
        String sql = "INSERT INTO appuser (first_name, last_name, email, password, phone, address, role_id, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Kiểm tra nếu createdAt là null, thì gán giá trị hiện tại
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());  // Gán giá trị mặc định nếu null
        }

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getPhone());
            stmt.setString(6, user.getAddress());
            stmt.setInt(7, user.getRoleId());  // Giả sử bạn gán roleId mặc định là 4 (Parent)
            stmt.setTimestamp(8, Timestamp.valueOf(user.getCreatedAt()));  // Gán createdAt (mới được thiết lập)

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));  // Set generated ID
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void saveParent(Parent parent) {
        String sql = "INSERT INTO parent (user_id, full_name, gender, phone) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, parent.getUserId(), parent.getFullName(), parent.getGender(), parent.getPhone());
    }
}
