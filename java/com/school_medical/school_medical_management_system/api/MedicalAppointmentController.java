package com.school_medical.school_medical_management_system.api;

import com.school_medical.school_medical_management_system.models.ApiResponse;
import com.school_medical.school_medical_management_system.models.ApproveAppointmentRequest;
import com.school_medical.school_medical_management_system.repositories.IUserRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Appuser;
import com.school_medical.school_medical_management_system.repositories.entites.MedicalAppointment;
import com.school_medical.school_medical_management_system.services.IMedicalAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class MedicalAppointmentController {

    @Autowired
    private IMedicalAppointmentService service;

    @Autowired
    private IUserRepository userRepository;

    /**
     * Tạo mới một cuộc hẹn
     */
    @PostMapping("/create")
    public ResponseEntity<?> createAppointment(@RequestBody MedicalAppointment appointment) {
        try {   
            // ✅ Lấy email người dùng từ JWT token
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();

            // ✅ Lấy thông tin người dùng theo email
            Appuser user = userRepository.getUserByEmail(email);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không tìm thấy người dùng.");
            }

            // ✅ Kiểm tra roleId không bị null
            if (user.getRoleId() == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Người dùng chưa được gán vai trò.");
            }

            // ✅ Kiểm tra bắt buộc phải có studentId
            if (appointment.getStudentId() == 0) {
                return ResponseEntity.badRequest().body("Vui lòng chọn học sinh.");
            }

            int roleId = user.getRoleId();
            int userId = user.getId();

            if (roleId == 3) { // 👩‍⚕️ Nurse
                appointment.setNurseId(userId);
                appointment.setParentUserId(null); // nurse tự tạo
            } else if (roleId == 4) { // 👪 Parent
                if (appointment.getNurseId() == 0) {
                    return ResponseEntity.badRequest().body("Phụ huynh phải chọn nurse.");
                }
                appointment.setParentUserId(userId); // parent đang đăng nhập
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vai trò không hợp lệ.");
            }

            // ✅ Trạng thái mặc định
            appointment.setStatus("Pending");

            // ✅ Gọi service lưu lịch hẹn
            service.createAppointment(appointment);

            return ResponseEntity.ok("Tạo lịch hẹn thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi hệ thống: " + e.getMessage());
        }
    }


    /**
     * Lấy tất cả cuộc hẹn của một sinh viên
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<MedicalAppointment>> getAppointmentsByStudent(@PathVariable int studentId) {
        List<MedicalAppointment> appointments = service.getAppointmentsByStudentId(studentId);
        return ResponseEntity.ok(appointments);
    }

    /**
     * Phê duyệt cuộc hẹn
     */
    @PutMapping("/approve")
    public ResponseEntity<?> approveAppointment(@RequestBody ApproveAppointmentRequest request) {
        try {
            String status = request.getStatus();
            if (!status.equalsIgnoreCase("Approved") && !status.equalsIgnoreCase("Rejected")) {
                return ResponseEntity.badRequest().body("Status must be 'Approved' or 'Rejected'");
            }
            service.approveAppointment(request.getAppointmentId(), status);
            return ResponseEntity.ok("Appointment " + status + " successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    /**
     * Lấy tất cả các cuộc hẹn
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<MedicalAppointment>>> viewAllAppointments() {
        try {
            List<MedicalAppointment> allAppointments = service.getAllAppointments();
            ApiResponse<List<MedicalAppointment>> response = new ApiResponse<>(true, "Lấy tất cả cuộc hẹn thành công", allAppointments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<MedicalAppointment>> response = new ApiResponse<>(false, "Lỗi khi lấy tất cả cuộc hẹn: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Cập nhật cuộc hẹn
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateAppointment(@RequestBody MedicalAppointment appointment) {
        try {
            service.updateAppointment(appointment);
            return ResponseEntity.ok("Appointment updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    /**
     * Xóa cuộc hẹn
     */
    @DeleteMapping("/delete/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable int appointmentId) {
        try {
            service.deleteAppointment(appointmentId);
            return ResponseEntity.ok("Appointment deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
