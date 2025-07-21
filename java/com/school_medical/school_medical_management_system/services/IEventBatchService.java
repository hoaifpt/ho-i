package com.school_medical.school_medical_management_system.services;

import com.school_medical.school_medical_management_system.repositories.entites.EventBatch;

import java.util.List;

public interface IEventBatchService {
    void createBatch(EventBatch batch);
    void approveBatch(Integer batchId);
    void resendBatch(Integer batchId);  // Thêm phương thức resend
    void deleteBatch(Integer batchId);  // Thêm phương thức delete
    List<EventBatch> getAllBatches();
    EventBatch getBatchById(Integer batchId);
    List<EventBatch> getTop3UpcomingEvents();
}

