package com.school_medical.school_medical_management_system.api;

import com.school_medical.school_medical_management_system.repositories.entites.MedicalSupply;
import com.school_medical.school_medical_management_system.services.IMedicalSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicalsupply")
public class MedicalSupplyController {

    @Autowired
    private IMedicalSupplyService service;

    @PostMapping("/create")
    public ResponseEntity<String> createSupply(@RequestBody MedicalSupply supply) {
        service.createSupply(supply);
        return ResponseEntity.ok("Supply created successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<MedicalSupply>> getAllSupplies() {
        return ResponseEntity.ok(service.getAllSupplies()); // đã lọc status = 1 trong repo
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSupplyById(@PathVariable Integer id) {
        MedicalSupply supply = service.findById(id);
        if (supply == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Supply not found or already deleted");
        }
        return ResponseEntity.ok(supply);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSupply(@RequestBody MedicalSupply supply) {
        MedicalSupply existing = service.findById(supply.getSupplyId());
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Supply not found or already deleted");
        }

        // giữ nguyên status cũ nếu không truyền từ client
        if (supply.getStatus() == null) {
            supply.setStatus(existing.getStatus());
        }

        service.updateSupply(supply);
        return ResponseEntity.ok("Supply updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupply(@PathVariable Integer id) {
        MedicalSupply existing = service.findById(id);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Supply not found or already deleted");
        }
        service.deleteSupply(id); // soft delete: status = 0
        return ResponseEntity.ok("Supply deleted successfully");
    }
}
