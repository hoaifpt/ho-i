package com.school_medical.school_medical_management_system.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school_medical.school_medical_management_system.repositories.entites.Report;
import com.school_medical.school_medical_management_system.services.IReportService;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final IReportService reportService;

    @Autowired
    public ReportController(IReportService reportService) {
        this.reportService = reportService;
    }

    // Thêm báo cáo
    @PostMapping("/add")
    public ResponseEntity<String> addReport(@RequestBody Report report) {
        reportService.saveReport(report);
        return ResponseEntity.status(201).body("Report added successfully");
    }

    // Cập nhật báo cáo
    @PutMapping("/update")
    public ResponseEntity<String> updateReport(@RequestBody Report report) {
        try {
            reportService.modifyReport(report);
            return ResponseEntity.ok("Report updated successfully");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(404).body("Report not found for update");
        }
    }

    // Xóa báo cáo
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable int id) {
        try {
            reportService.removeReport(id);
            return ResponseEntity.ok("Report deleted successfully");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(404).body("Report not found for deletion");
        }
    }

    // Lấy báo cáo theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getReport(@PathVariable int id) {
        Report report = reportService.getReport(id);

        if (report == null) {
            // Nếu không tìm thấy báo cáo, trả về thông báo không tìm thấy với mã lỗi 404
            return ResponseEntity.status(404).body("Report with ID " + id + " not found");
        }

        // Trả về đối tượng báo cáo nếu tìm thấy
        return ResponseEntity.ok(report);
    }

        //view all report
    @GetMapping
    public List<Report> getAllReports() {
        return reportService.getAllReports(); // ✅ Trả về tất cả báo cáo
    }

}
