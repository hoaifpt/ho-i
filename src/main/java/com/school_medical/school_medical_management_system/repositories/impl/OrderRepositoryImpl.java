package com.school_medical.school_medical_management_system.repositories.impl;

import com.school_medical.school_medical_management_system.repositories.IOrderRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryImpl implements IOrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM mesch.order";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setAmount(rs.getDouble("amount"));
            order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            order.setOrderInfo(rs.getString("order_info"));
            order.setStatus(rs.getString("status"));
            order.setTxnRef(rs.getString("txn_ref"));
            return order;
        });
    }
}
