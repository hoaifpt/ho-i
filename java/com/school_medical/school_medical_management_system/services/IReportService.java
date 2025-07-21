package com.school_medical.school_medical_management_system.services;

import com.school_medical.school_medical_management_system.repositories.entites.Report;
import java.util.List;

public interface IReportService {
    void saveReport(Report report);
    void modifyReport(Report report);
    void removeReport(int reportId);
    Report getReport(int reportId);

    List<Report> getAllReports(); // ✅ Thêm mới
}

