package com.school_medical.school_medical_management_system.repositories;

import com.school_medical.school_medical_management_system.repositories.entites.EventBatch;

import java.util.List;

public interface IEventBatchRepository {
    void createBatch(EventBatch batch);
    void approveBatch(Integer batchId);
    void resendBatch(Integer batchId);  // Thêm phương thức resend
    void deleteBatch(Integer batchId);  // Thêm phương thức delete (chuyển trạng thái thành "Deleted")
    List<EventBatch> getAllBatches();  // Lấy tất cả các batch, không lấy những cái đã bị xóa
    EventBatch getBatchById(Integer batchId);  // Lấy thông tin chi tiết của một batch theo ID
    List<EventBatch> findTop3UpcomingEvents();  // Lấy 3 sự kiện tiếp theo
}
