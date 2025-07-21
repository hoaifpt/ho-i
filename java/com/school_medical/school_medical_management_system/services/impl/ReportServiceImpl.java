package com.school_medical.school_medical_management_system.services.impl;

import com.school_medical.school_medical_management_system.repositories.IReportRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Report;
import com.school_medical.school_medical_management_system.services.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements IReportService {

    private final IReportRepository reportRepository;

    @Autowired
    public ReportServiceImpl(IReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public void saveReport(Report report) {
        reportRepository.addReport(report);
    }

    @Override
    public void modifyReport(Report report) {
        reportRepository.updateReport(report);
    }

    @Override
    public void removeReport(int reportId) {
        reportRepository.deleteReport(reportId);
    }

    @Override
    public Report getReport(int reportId) {
        return reportRepository.getReportById(reportId);
    }

    @Override
    public List<Report> getAllReports() {
        return reportRepository.getAllReports();
    }
}
