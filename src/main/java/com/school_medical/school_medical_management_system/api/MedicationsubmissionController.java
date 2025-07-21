package com.school_medical.school_medical_management_system.api;

import com.school_medical.school_medical_management_system.models.ApprovalRequest;
import com.school_medical.school_medical_management_system.repositories.entites.Medicationsubmission;
import com.school_medical.school_medical_management_system.services.IAppUserService;
import com.school_medical.school_medical_management_system.services.IMedicationsubmissionService;
import com.school_medical.school_medical_management_system.repositories.IUserRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Appuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medication-submissions")
public class MedicationsubmissionController {

    @Autowired
    private IMedicationsubmissionService service;

    @Autowired
    private IAppUserService appUserService;

    // ✅ Phụ huynh gửi đơn - approvedBy luôn null
    @PostMapping
    public ResponseEntity<String> createSubmission(@RequestBody Medicationsubmission submission) {
        submission.setApprovedBy(null);
        if (submission.getStatus() == null || submission.getStatus().isEmpty()) {
            submission.setStatus("PENDING");
        }
        service.save(submission);
        return ResponseEntity.ok("Medication submission saved successfully.");
    }

    // ✅ Lấy danh sách đơn thuốc của con cái phụ huynh đang đăng nhập
    @GetMapping("/my-submissions")
    public ResponseEntity<List<Medicationsubmission>> getSubmissionsByCurrentParent(
            @AuthenticationPrincipal User user) {
        String email = user.getUsername(); // Lấy email từ JWT
        Appuser appuser = appUserService.getUserByEmail(email); // Truy lại thông tin người dùng

        if (appuser == null) {
            return ResponseEntity.badRequest().build();
        }

        Integer parentId = appuser.getId(); // Lấy ID phụ huynh từ Appuser
        List<Medicationsubmission> submissions = service.findByParentId(parentId); // Truy vấn đơn thuốc của các con
        return ResponseEntity.ok(submissions);
    }

    // ✅ Lấy chi tiết đơn theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Medicationsubmission> getSubmissionById(@PathVariable Integer id) {
        Medicationsubmission submission = service.findById(id);
        if (submission != null) {
            return ResponseEntity.ok(submission);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ School Nurse duyệt đơn: lấy approvedBy từ token
    @PutMapping("/{id}/approve")
    public ResponseEntity<String> approveSubmission(@PathVariable Integer id,
            @RequestBody ApprovalRequest request,
            @AuthenticationPrincipal User user) {
        String email = user.getUsername();
        Appuser appuser = appUserService.getUserByEmail(email);

        if (appuser == null) {
            return ResponseEntity.badRequest().body("Invalid user.");
        }

        Long approvedByUserId = appuser.getId().longValue();
        service.approveSubmission(id, approvedByUserId, request.getApprovalStatus());
        return ResponseEntity.ok("Submission approval status updated successfully.");
    }

    // Thêm thuốc cho đơn thuốc
    @PostMapping("/api/medication-submissions")
    public ResponseEntity<?> createMedications(@RequestBody List<Medicationsubmission> medications) {
        // Xử lý danh sách thuốc
        return ResponseEntity.ok("Thêm thuốc thành công");
    }
}
