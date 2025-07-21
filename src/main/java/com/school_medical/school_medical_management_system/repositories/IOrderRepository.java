package com.school_medical.school_medical_management_system.repositories;

import com.school_medical.school_medical_management_system.repositories.entites.Order;
import java.util.List;

public interface IOrderRepository {
    List<Order> getAllOrders();  // Lấy tất cả các đơn hàng
}

