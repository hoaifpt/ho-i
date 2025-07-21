package com.school_medical.school_medical_management_system.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import com.school_medical.school_medical_management_system.models.VaccinationParentDeclarationDTO;
import com.school_medical.school_medical_management_system.repositories.entites.Appuser;
import com.school_medical.school_medical_management_system.services.IAppUserService;
import com.school_medical.school_medical_management_system.services.IParentStudentService;
import com.school_medical.school_medical_management_system.services.IVaccinationParentDeclarationService;

@RestController
@RequestMapping("/api/vaccination-history")
public class VaccinationParentDeclarationController {

    @Autowired
    private IVaccinationParentDeclarationService service;

    @Autowired
    private IAppUserService appUserService;

    @Autowired
    private IParentStudentService parentStudentService;


    // ✅ Phụ huynh khai báo lịch sử tiêm chủng cho con
    @PostMapping
    public ResponseEntity<?> declareVaccinationHistory(
            @AuthenticationPrincipal User user,
            @RequestBody VaccinationParentDeclarationDTO dto) {

        String email = user.getUsername();
        Appuser parent = appUserService.getUserByEmail(email);
        if (parent == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid parent user");
        }

        // Kiểm tra quyền khai báo
        if (!parentStudentService.isStudentBelongsToParent(parent.getId(), dto.getStudentId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to declare for this student.");
        }

        // Gán parentId vào DTO trước khi lưu
        dto.setParentId(parent.getId());

        service.save(dto);
        return ResponseEntity.ok("Vaccination history declared successfully.");
    }


    // ✅ Phụ huynh xem toàn bộ lịch sử tiêm chủng của các con
    @GetMapping("/my-children")
    public ResponseEntity<?> getHistoryOfMyChildren(@AuthenticationPrincipal User user) {
        String email = user.getUsername();
        Appuser parent = appUserService.getUserByEmail(email);
        if (parent == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid parent user");
        }

        // Lấy danh sách student_id của con
        List<Integer> studentIds = parentStudentService.getStudentIdsByParentId(parent.getId());
        if (studentIds.isEmpty()) {
            return ResponseEntity.ok("No students associated with this parent.");
        }

        // Lấy danh sách lịch sử tiêm chủng cho tất cả học sinh
        List<VaccinationParentDeclarationDTO> allDeclarations = new ArrayList<>();
        for (int studentId : studentIds) {
            allDeclarations.addAll(service.getAllByStudentId(studentId));
        }

        return ResponseEntity.ok(allDeclarations);
    }

    @DeleteMapping("/{id}")
public ResponseEntity<?> deleteDeclaration(
        @AuthenticationPrincipal User user,
        @PathVariable int id) {

    String email = user.getUsername();
    Appuser parent = appUserService.getUserByEmail(email);
    if (parent == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid parent user");
    }

    // Có thể mở rộng xác thực: chỉ được xoá nếu là parent của học sinh khai báo đó

    service.deleteById(id);
    return ResponseEntity.ok("Vaccination declaration deleted successfully.");
}

    // ✅ Cập nhật thông tin khai báo vaccine
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVaccinationHistory(
            @AuthenticationPrincipal User user,
            @PathVariable int id,
            @RequestBody VaccinationParentDeclarationDTO dto) {

        String email = user.getUsername();
        Appuser parent = appUserService.getUserByEmail(email);
        if (parent == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid parent user");
        }

        // Kiểm tra quyền cập nhật
        if (!parentStudentService.isStudentBelongsToParent(parent.getId(), dto.getStudentId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to update for this student.");
        }

        // Gán parentId vào DTO trước khi lưu
        dto.setParentId(parent.getId());
        dto.setId(id);  // Đảm bảo ID được gán đúng

        // Cập nhật thông tin khai báo vaccine
        service.update(dto);
        return ResponseEntity.ok("Vaccination history updated successfully.");
    }

}