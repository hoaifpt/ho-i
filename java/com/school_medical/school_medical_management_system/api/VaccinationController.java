package com.school_medical.school_medical_management_system.api;

import com.school_medical.school_medical_management_system.models.ApprovalRequest;
import com.school_medical.school_medical_management_system.models.VaccinationRequest;
import com.school_medical.school_medical_management_system.repositories.entites.Vaccination;
import com.school_medical.school_medical_management_system.services.IVaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaccinations")
public class VaccinationController {

    @Autowired
    private IVaccinationService vaccinationService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getVaccinationById(@PathVariable Integer id) {
        Vaccination vac = vaccinationService.getVaccinationById(id);
        if (vac == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vac);
    }

    @GetMapping
    public ResponseEntity<?> getAllVaccinations() {
        List<Vaccination> vaccinations = vaccinationService.getAllVaccinations();
        return ResponseEntity.ok(vaccinations);
    }

    @PostMapping
    public ResponseEntity<?> createVaccination(@RequestBody VaccinationRequest request) {
        vaccinationService.createVaccination(request);
        return ResponseEntity.ok("Vaccination created successfully.");
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<String> approveVaccination(@PathVariable Integer id,
                                                     @RequestBody ApprovalRequest request) {
        vaccinationService.approveVaccination(id, request.getApprovalStatus());
        return ResponseEntity.ok("Vaccination status updated successfully");
    }
}
