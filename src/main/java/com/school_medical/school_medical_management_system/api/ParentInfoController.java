package com.school_medical.school_medical_management_system.api;

import com.school_medical.school_medical_management_system.models.ParentInfoDTO;
import com.school_medical.school_medical_management_system.repositories.entites.Appuser;
import com.school_medical.school_medical_management_system.services.IAppUserService;
import com.school_medical.school_medical_management_system.services.IParentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parent-info")
public class ParentInfoController {

    @Autowired
    private IParentInfoService parentInfoService;

    @Autowired
    private IAppUserService appUserService;

    // ✅ Lấy thông tin phụ huynh từ user đăng nhập
    @GetMapping("/me")
    public ResponseEntity<ParentInfoDTO> getMyParentInfo(@AuthenticationPrincipal User user) {
        String email = user.getUsername(); // lấy từ token
        Appuser appuser = appUserService.getUserByEmail(email);

        if (appuser == null) {
            return ResponseEntity.badRequest().build();
        }

        ParentInfoDTO dto = parentInfoService.getParentInfoByUserId(appuser.getId());
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
