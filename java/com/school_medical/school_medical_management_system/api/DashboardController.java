package com.school_medical.school_medical_management_system.api;

import com.school_medical.school_medical_management_system.models.DashboardSummary;
import com.school_medical.school_medical_management_system.services.IDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private IDashboardService dashboardService;

    @GetMapping("/summary")
    public DashboardSummary getDashboardSummary() {
        return dashboardService.getSummary();
    }
}
