package com.school_medical.school_medical_management_system.api;

import com.school_medical.school_medical_management_system.models.UpdateSupplyRequest;
import com.school_medical.school_medical_management_system.repositories.entites.MedicalEventSupply;
import com.school_medical.school_medical_management_system.services.IMedicalEventSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event-supplies")
public class MedicalEventSupplyController {

    @Autowired
    private IMedicalEventSupplyService service;

    // Thêm vật tư vào sự kiện y tế (Giảm tồn kho)
    @PostMapping("/add")
    public ResponseEntity<?> addSupplyToEvent(@RequestBody MedicalEventSupply request) {
        try {
            service.addSupplyToEvent(request);
            return ResponseEntity.ok("Supply added successfully");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("An error occurred");
        }
    }

    // Cập nhật số lượng vật tư đã dùng (Điều chỉnh tồn kho + event supply)
    @PutMapping("/{eventSupplyId}")
    public ResponseEntity<?> updateSupplyUsed(@PathVariable Integer eventSupplyId,
                                              @RequestBody UpdateSupplyRequest request) {
        try {
            service.updateSupplyUsed(eventSupplyId, request.getNewQuantity());
            return ResponseEntity.ok("Supply usage updated successfully");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("An unexpected error occurred");
        }
    }

    // Xem danh sách vật tư đã dùng trong 1 sự kiện cụ thể
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<MedicalEventSupply>> getSuppliesByEvent(@PathVariable Integer eventId) {
        return ResponseEntity.ok(service.getSuppliesByEvent(eventId));
    }

    // ✅ Xem tất cả vật tư đã dùng trong tất cả các sự kiện
    @GetMapping("/all")
    public ResponseEntity<List<MedicalEventSupply>> getAllEventSupplies() {
        return ResponseEntity.ok(service.getAllEventSupplies());
    }
}
