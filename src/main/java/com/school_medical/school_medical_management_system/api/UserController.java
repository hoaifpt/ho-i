package com.school_medical.school_medical_management_system.api;

import com.school_medical.school_medical_management_system.models.ApiResponse;
import com.school_medical.school_medical_management_system.models.UserProfileResponse;
import com.school_medical.school_medical_management_system.repositories.entites.Appuser;
import com.school_medical.school_medical_management_system.services.IAppUserService;
import com.school_medical.school_medical_management_system.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private IAppUserService appUserService;

    @GetMapping("/me")
    public UserProfileResponse getMyProfile() {
        String currentEmail = AuthUtils.getCurrentUserEmail(); // ✅ dùng static
        Appuser user = appUserService.getUserByEmail(currentEmail);

        UserProfileResponse response = new UserProfileResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhone(user.getPhone());
        response.setAddress(user.getAddress());
        response.setRoleName(user.getRoleName());
        response.setCreatedAt(user.getCreatedAt());

        return response;
    }

    @GetMapping("/nurses")
    public List<Appuser> getAllNurses() {
        return appUserService.getAllNurses();
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Appuser>>> getAllUsers() {
        try {
            List<Appuser> users = appUserService.getAllUsers();
            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy tất cả người dùng thành công", users));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Lỗi hệ thống: " + e.getMessage(), null));
        }
    }

    // Endpoint đăng ký tài khoản
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Appuser user) {
        // Đăng ký người dùng
        Appuser registeredUser = appUserService.registerUser(user);

        // Trả về thông báo thành công với mã trạng thái CREATED (201)
        return new ResponseEntity<>("Tạo tài khoản và thêm thông tin phụ huynh thành công", HttpStatus.CREATED);
    }
}
