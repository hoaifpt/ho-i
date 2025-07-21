package com.school_medical.school_medical_management_system.services.impl;

import com.school_medical.school_medical_management_system.config.JwtUtil;
import com.school_medical.school_medical_management_system.repositories.IUserRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Appuser;
import com.school_medical.school_medical_management_system.repositories.entites.Parent;
import com.school_medical.school_medical_management_system.services.IAppUserService;
import com.school_medical.school_medical_management_system.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppUserService implements IAppUserService {
    @Autowired
    private IUserRepository userRepository;

    private AuthUtils authUtils;

    @Override
    public Appuser getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public List<Appuser> getAllNurses() {
        return userRepository.getAllNurses();
    }

    @Override
    public List<Appuser> getAllUsers() {
        return userRepository.getAllUsers();
    }


    public Appuser registerUser(Appuser user) {
        // Kiểm tra nếu đối tượng user là null
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User data is missing");
        }

        // Kiểm tra các trường bắt buộc của người dùng
        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name is required");
        }

        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Last name is required");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty() || !isValidEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email");
        }

        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number is required");
        }

        if (user.getAddress() == null || user.getAddress().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Address is required");
        }

        // Kiểm tra nếu email đã tồn tại
        if (getUserByEmail(user.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        // Gán roleId mặc định là 4 (Parent)
        user.setRoleId(4);

        // Gán thời gian tạo nếu chưa có
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());  // Gán thời gian hiện tại nếu chưa có
        }

        // Lưu người dùng vào cơ sở dữ liệu
        Appuser registeredUser = userRepository.saveUser(user);

        // Thêm thông tin phụ huynh vào bảng 'parent'
        Parent parent = new Parent();
        parent.setUserId(registeredUser.getId());  // Gán user_id từ Appuser
        parent.setFullName(registeredUser.getFirstName() + " " + registeredUser.getLastName());  // Kết hợp first name và last name
        parent.setPhone(registeredUser.getPhone());  // Gán số điện thoại
        parent.setGender(registeredUser.getGender());  // Gán giới tính từ Appuser

// Nếu giới tính không được cung cấp, bạn có thể gán mặc định ở đây
        if (parent.getGender() == null || parent.getGender().isEmpty()) {
            parent.setGender("Nam");  // Nếu không có giới tính, gán mặc định là Nam
        }
        // Lưu thông tin phụ huynh vào bảng parent
        userRepository.saveParent(parent);

        return registeredUser;
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }
}
