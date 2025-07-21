package com.school_medical.school_medical_management_system.VNPAY;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByTxnRef(String txnRef);

    List<Order> findByUserId(Long userId);

    List<Order> findByUserIdAndStatus(Long userId, String status);

    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
}
