package com.school_medical.school_medical_management_system.api;

import com.school_medical.school_medical_management_system.models.ApiResponse;
import com.school_medical.school_medical_management_system.models.ApprovalRequest;
import com.school_medical.school_medical_management_system.repositories.entites.MedicalEvent;
import com.school_medical.school_medical_management_system.services.IMedicalEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-events")
public class MedicalEventController {

    @Autowired
    private IMedicalEventService medicalEventService;

    /**
     * Lấy danh sách tất cả sự kiện y tế
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<MedicalEvent>>> getAllMedicalEvents() {
        try {
            List<MedicalEvent> events = medicalEventService.getAllEvents();
            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy danh sách sự kiện thành công", events));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Lỗi hệ thống: " + e.getMessage(), null));
        }
    }

    /**
     * Tạo mới sự kiện y tế
     */
    @PostMapping
    public ResponseEntity<ApiResponse<MedicalEvent>> createMedicalEvent(@RequestBody MedicalEvent event) {
        try {
            MedicalEvent createdEvent = medicalEventService.createEvent(event);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Tạo sự kiện thành công", createdEvent));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Tạo thất bại: " + e.getMessage(), null));
        }
    }

    /**
     * Cập nhật sự kiện y tế theo ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MedicalEvent>> updateMedicalEvent(@PathVariable Long id, @RequestBody MedicalEvent event) {
        try {
            MedicalEvent updatedEvent = medicalEventService.updateEvent(id, event);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cập nhật sự kiện thành công", updatedEvent));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Cập nhật thất bại: " + e.getMessage(), null));
        }
    }

    /**
     * Phê duyệt sự kiện y tế theo ID
     */
    @PostMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<MedicalEvent>> approveMedicalEvent(@PathVariable Long id, @RequestBody ApprovalRequest approvalRequest) {
        try {
            MedicalEvent approvedEvent = medicalEventService.approveEvent(id, approvalRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Phê duyệt sự kiện thành công", approvedEvent));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Phê duyệt thất bại: " + e.getMessage(), null));
        }
    }

    /**
     * Xem chi tiết sự kiện y tế theo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MedicalEvent>> getMedicalEventById(@PathVariable Long id) {
        try {
            MedicalEvent event = medicalEventService.getEventById(id);
            if (event != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Lấy sự kiện thành công", event));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Sự kiện không tồn tại", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Lỗi hệ thống: " + e.getMessage(), null));
        }
    }
}
