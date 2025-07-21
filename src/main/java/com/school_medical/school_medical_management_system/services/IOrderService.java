package com.school_medical.school_medical_management_system.services;

import com.school_medical.school_medical_management_system.repositories.entites.Order;
import java.util.List;

public interface IOrderService {
    List<Order> getAllOrders();  // Lấy tất cả các đơn hàng
}

