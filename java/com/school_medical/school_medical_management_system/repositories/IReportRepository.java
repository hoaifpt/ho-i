package com.school_medical.school_medical_management_system.repositories;

import com.school_medical.school_medical_management_system.repositories.entites.Report;

import java.util.List;

public interface IReportRepository {
    void addReport(Report report);
    void updateReport(Report report);
    void deleteReport(int reportId);
    Report getReportById(int reportId);

    List<Report> getAllReports(); // ✅ Thêm mới
}

