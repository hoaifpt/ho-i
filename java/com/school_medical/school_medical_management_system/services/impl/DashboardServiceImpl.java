package com.school_medical.school_medical_management_system.services.impl;

import com.school_medical.school_medical_management_system.models.DashboardSummary;
import com.school_medical.school_medical_management_system.repositories.IDashboardRepository;
import com.school_medical.school_medical_management_system.services.IDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements IDashboardService {

    @Autowired
    private IDashboardRepository repository;

    @Override
    public DashboardSummary getSummary() {
        return repository.getSummary();
    }
}
