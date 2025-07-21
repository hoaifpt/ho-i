package com.school_medical.school_medical_management_system.api;

import com.school_medical.school_medical_management_system.models.ApiResponse;
import com.school_medical.school_medical_management_system.repositories.entites.Healthinfo;
import com.school_medical.school_medical_management_system.services.impl.HealthInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/healthinfo")
public class HealthInfoController {

    @Autowired
    private HealthInfoService service;

    // GET - Xem hồ sơ
    @GetMapping("/{studentId}")
    public ResponseEntity<ApiResponse<Healthinfo>> getHealthInfo(@PathVariable int studentId) {
        try {
            Healthinfo info = service.getHealthInfoByStudentId(studentId);
            if (info == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Không tìm thấy hồ sơ", null));
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy hồ sơ thành công", info));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Lỗi hệ thống: " + e.getMessage(), null));
        }
    }

    // POST - Lưu/Sửa hồ sơ
    @PutMapping("/save/{studentId}")
    public ResponseEntity<?> updateHealthInfo(@PathVariable int studentId, @RequestBody Healthinfo info) {
        try {
            service.saveOrUpdate(studentId, info);
            return ResponseEntity.ok("Cập nhật thành công");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi: " + e.getMessage());
        }
    }
}
