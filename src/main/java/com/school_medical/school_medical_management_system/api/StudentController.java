package com.school_medical.school_medical_management_system.api;

import com.school_medical.school_medical_management_system.models.ApiResponse;
import com.school_medical.school_medical_management_system.models.StudentHealthRequest;
import com.school_medical.school_medical_management_system.repositories.IUserRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Appuser;
import com.school_medical.school_medical_management_system.repositories.entites.Healthinfo;
import com.school_medical.school_medical_management_system.repositories.entites.Student;
import com.school_medical.school_medical_management_system.repositories.entites.VaccinationParentDeclaration;
import com.school_medical.school_medical_management_system.services.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final IStudentService studentService;
    private final IUserRepository userRepository;

    @Autowired
    public StudentController(IStudentService studentService, IUserRepository userRepository) {
        this.studentService = studentService;
        this.userRepository = userRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<String>> saveStudentWithHealthInfoAndLinkParent(@RequestBody StudentHealthRequest request) {
        // Lấy email từ JWT đã giải mã
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        // Truy vấn parent_user_id từ database
        Appuser parent = userRepository.getUserByEmail(email);
        if (parent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Không tìm thấy phụ huynh với email: " + email, null));
        }
        int parentUserId = parent.getId();

        // Tạo đối tượng Student và Healthinfo
        Student student = new Student();
        student.setName(request.getStudentName());
        student.setDateOfBirth(request.getDob().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        student.setGender(request.getGender());
        student.setGrade(request.getGrade());
        student.setClassId(request.getClassId());

        Healthinfo healthinfo = new Healthinfo();
        healthinfo.setAllergy(request.getAllergy());
        healthinfo.setChronicDisease(request.getChronicDisease());
        healthinfo.setVision(request.getVision());
        healthinfo.setHearing(request.getHearing());
        healthinfo.setMedicalHistory(request.getMedicalHistory());
        healthinfo.setHeight(request.getHeight());
        healthinfo.setWeight(request.getWeight());
        healthinfo.setBmi(request.getBmi());

        // Lưu học sinh, thông tin sức khỏe và mối quan hệ phụ huynh
        studentService.saveStudentWithHealthInfoAndLinkParent(student, healthinfo, parentUserId, request.getRelationship());

        return ResponseEntity.ok(new ApiResponse<>(true, "Học sinh và thông tin sức khỏe đã được lưu và liên kết với phụ huynh!", "Success"));
    }

    /**
     * Lấy tất cả học sinh
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy danh sách học sinh thành công", students));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Lỗi hệ thống: " + e.getMessage(), null));
        }
    }

    /**
     * Lấy danh sách học sinh của phụ huynh
     */
    @GetMapping("/by-parent/{parentUserId}")
    public ResponseEntity<ApiResponse<List<Student>>> getStudentsByParentId(@PathVariable int parentUserId) {
        try {
            List<Student> students = studentService.getStudentsByParentId(parentUserId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy danh sách học sinh thành công", students));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Lỗi hệ thống: " + e.getMessage(), null));
        }
    }

    @GetMapping("/healthinfo")
    public ResponseEntity<List<Healthinfo>> getHealthInfoByUserId(@RequestParam int userId) {
        // Gọi service để lấy dữ liệu
        List<Healthinfo> healthInfos = studentService.getHealthInfoByUserId(userId);

        // Kiểm tra nếu không có dữ liệu
        if (healthInfos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Không tìm thấy dữ liệu
        }

        // Trả về dữ liệu
        return ResponseEntity.ok(healthInfos);
    }

    @GetMapping("/vaccination-info")
    public ResponseEntity<List<VaccinationParentDeclaration>> getVaccinationInfoByUserId(@RequestParam int userId) {
        // Lấy thông tin tiêm chủng của học sinh từ userId (parent_user_id)
        List<VaccinationParentDeclaration> vaccinationInfos = studentService.getVaccinationInfoByUserId(userId);

        // Nếu không có dữ liệu
        if (vaccinationInfos.isEmpty()) {
            return ResponseEntity.status(404).body(null);  // Trả về 404 nếu không có dữ liệu
        }

        // Trả về dữ liệu tiêm chủng
        return ResponseEntity.ok(vaccinationInfos);
    }

    @GetMapping("/my-children")
    public ResponseEntity<ApiResponse<List<Student>>> getMyChildren() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();

            Appuser parent = userRepository.getUserByEmail(email);
            if (parent == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(false, "Không tìm thấy phụ huynh với email: " + email, null));
            }

            // ✅ GỌI ĐÚNG SERVICE
            List<Student> students = studentService.getStudentsByParentUserId(parent.getId());

            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy danh sách học sinh thành công", students));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Lỗi hệ thống: " + e.getMessage(), null));
        }
    }


    @GetMapping("/parent-email/{studentId}")
    public ResponseEntity<String> getParentEmailByStudentId(@PathVariable int studentId) {
        Optional<Appuser> parentEmail = studentService.getParentEmailByStudentId(studentId);

        // Nếu tìm thấy email phụ huynh, trả về email
        if (parentEmail.isPresent()) {
            return ResponseEntity.ok(parentEmail.get().getEmail());
        }

        // Nếu không tìm thấy, trả về thông báo lỗi
        return ResponseEntity.status(404).body("Parent not found for student ID: " + studentId);
    }
}
