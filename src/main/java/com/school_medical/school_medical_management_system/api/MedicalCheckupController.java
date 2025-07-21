package com.school_medical.school_medical_management_system.api;

import com.school_medical.school_medical_management_system.models.ApprovalRequest;
import com.school_medical.school_medical_management_system.models.MedicalCheckupRequest;
import com.school_medical.school_medical_management_system.repositories.entites.Medicalcheckup;
import com.school_medical.school_medical_management_system.services.IMedicalCheckupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-checkups")
public class MedicalCheckupController {

    @Autowired
    private IMedicalCheckupService medicalCheckupService;

    // ✅ Xem 1 Medical Checkup theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCheckupById(@PathVariable Integer id) {
        Medicalcheckup checkup = medicalCheckupService.getCheckupById(id);
        if (checkup == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(checkup);
    }

    // ✅ Xem tất cả Medical Checkup
    @GetMapping
    public ResponseEntity<?> getAllCheckups() {
        List<Medicalcheckup> checkups = medicalCheckupService.getAllCheckups();
        return ResponseEntity.ok(checkups);
    }

    // ✅ Xem tất cả Medical Checkup
    @PostMapping
    public ResponseEntity<?> createCheckup(@RequestBody MedicalCheckupRequest request) {
        medicalCheckupService.createCheckup(request);
        return ResponseEntity.ok("Medical Checkup created successfully.");
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<String> approveCheckup(@PathVariable Integer id,
                                                 @RequestBody ApprovalRequest request) {
        medicalCheckupService.approveCheckup(id, request.getApprovalStatus());
        return ResponseEntity.ok("Medical checkup status updated successfully");
    }
}
