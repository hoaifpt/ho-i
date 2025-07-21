package com.school_medical.school_medical_management_system.services.impl;

import com.school_medical.school_medical_management_system.repositories.IEventBatchRepository;
import com.school_medical.school_medical_management_system.repositories.entites.EventBatch;
import com.school_medical.school_medical_management_system.services.IEventBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventBatchService implements IEventBatchService {
    @Autowired
    private IEventBatchRepository eventBatchRepository;

    @Override
    public void createBatch(EventBatch batch) {
        eventBatchRepository.createBatch(batch);
    }

    @Override
    public void approveBatch(Integer batchId) {
        eventBatchRepository.approveBatch(batchId);
    }

    @Override
    public List<EventBatch> getAllBatches() {
        return eventBatchRepository.getAllBatches();
    }

    @Override
    public EventBatch getBatchById(Integer batchId) {
        return eventBatchRepository.getBatchById(batchId);
    }

    @Override
    public List<EventBatch> getTop3UpcomingEvents() {
        return eventBatchRepository.findTop3UpcomingEvents();
    }

    @Override
    public void resendBatch(Integer batchId) {
        eventBatchRepository.resendBatch(batchId);  // Gọi phương thức resend trong repository
    }

    @Override
    public void deleteBatch(Integer batchId) {
        eventBatchRepository.deleteBatch(batchId);  // Gọi phương thức delete trong repository
    }


}
