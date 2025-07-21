package com.school_medical.school_medical_management_system.repositories.impl;

import com.school_medical.school_medical_management_system.repositories.IStudentRepository;
import com.school_medical.school_medical_management_system.repositories.entites.Appuser;
import com.school_medical.school_medical_management_system.repositories.entites.Healthinfo;
import com.school_medical.school_medical_management_system.repositories.entites.Student;
import com.school_medical.school_medical_management_system.repositories.entites.VaccinationParentDeclaration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepositoryImpl implements IStudentRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int saveStudent(Student student) {
        String sql = "INSERT INTO student (name, date_of_birth, gender, grade, class_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, student.getName(), student.getDateOfBirth(), student.getGender(),
                student.getGrade(), student.getClassId());  // Thêm classId vào câu lệnh

        // Lấy student_id của học sinh vừa được tạo ra
        String getLastIdSql = "SELECT LAST_INSERT_ID()";
        return jdbcTemplate.queryForObject(getLastIdSql, Integer.class); // Lấy student_id mới
    }

    @Override
    public int saveHealthInfo(Healthinfo healthInfo) {
        String sql = "INSERT INTO healthinfo (student_id, allergy, chronic_disease, vision, hearing, medical_history, height, weight, bmi) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, healthInfo.getStudentId(), healthInfo.getAllergy(), healthInfo.getChronicDisease(),
                healthInfo.getVision(), healthInfo.getHearing(), healthInfo.getMedicalHistory(),
                healthInfo.getHeight(), healthInfo.getWeight(), healthInfo.getBmi());
    }

    @Override
    public void saveParentStudent(int parentUserId, int studentId, String relationship) {
        String sql = "INSERT INTO parentstudent (parent_user_id, student_id, relationship) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, parentUserId, studentId, relationship);  // Lưu thông tin mối quan hệ (cha/mẹ)
    }

    @Override
    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM student";  // SQL query để lấy tất cả học sinh
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Student student = new Student();
            student.setId(rs.getInt("student_id"));
            student.setName(rs.getString("name"));
            student.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
            student.setGender(rs.getString("gender"));
            student.setGrade(rs.getString("grade"));
            student.setClassId(rs.getInt("class_id"));
            return student;  // Trả về đối tượng Student đã được gán thông tin
        });
    }

    @Override
    public List<Student> getStudentsByParentId(int parentUserId) {
        String sql = "SELECT s.student_id, s.name, s.date_of_birth, s.gender, s.grade, s.class_id " +
                "FROM student s " +
                "JOIN parentstudent ps ON s.student_id = ps.student_id " +
                "WHERE ps.parent_user_id = ?";

        return jdbcTemplate.query(sql, new Object[]{parentUserId}, (rs, rowNum) -> {
            Student student = new Student();
            student.setId(rs.getInt("student_id"));
            student.setName(rs.getString("name"));
            student.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
            student.setGender(rs.getString("gender"));
            student.setGrade(rs.getString("grade"));
            student.setClassId(rs.getInt("class_id"));
            return student;
        });
    }

    @Override
    public List<Healthinfo> getHealthInfoByUserId(int userId) {
        String sql = """
    SELECT h.health_info_id, h.allergy, h.chronic_disease, h.vision, h.hearing, h.medical_history, h.height, h.weight, h.bmi, h.student_id
    FROM healthinfo h
    JOIN student s ON s.student_id = h.student_id
    JOIN parentstudent ps ON ps.student_id = s.student_id
    JOIN appuser a ON a.user_id = ps.parent_user_id
    WHERE a.user_id = ?
    """;

        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            Healthinfo healthinfo = new Healthinfo();
            healthinfo.setHealthInfoId(rs.getInt("health_info_id"));  // Đảm bảo bạn gán health_info_id
            healthinfo.setAllergy(rs.getString("allergy"));
            healthinfo.setChronicDisease(rs.getString("chronic_disease"));
            healthinfo.setVision(rs.getString("vision"));
            healthinfo.setHearing(rs.getString("hearing"));
            healthinfo.setMedicalHistory(rs.getString("medical_history"));
            healthinfo.setHeight(rs.getFloat("height"));
            healthinfo.setWeight(rs.getFloat("weight"));
            healthinfo.setBmi(rs.getFloat("bmi"));
            healthinfo.setStudentId(rs.getInt("student_id"));  // Đảm bảo bạn gán student_id
            return healthinfo;
        });
    }

    @Override
    public List<VaccinationParentDeclaration> getVaccinationInfoByUserId(int userId) {
        String sql = """
    SELECT v.id, v.student_id, v.parent_id, v.vaccine_name, v.declared_date, v.notes, v.status, v.dose_number, v.vaccine_lot, v.consent_verified
    FROM vaccinationparentdeclaration v
    JOIN parent p ON p.parent_id = v.parent_id
    JOIN student s ON s.student_id = v.student_id
    JOIN appuser a ON a.user_id = p.user_id
    WHERE a.user_id = ?
    """;

        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            VaccinationParentDeclaration vaccination = new VaccinationParentDeclaration();
            vaccination.setId(rs.getInt("id"));
            vaccination.setStudentId(rs.getInt("student_id"));
            vaccination.setParentId(rs.getInt("parent_id"));
            vaccination.setVaccinationName(rs.getString("vaccine_name"));
            vaccination.setDeclaredDate(rs.getDate("declared_date").toLocalDate());
            vaccination.setNotes(rs.getString("notes"));
            vaccination.setStatus(rs.getString("status"));
            vaccination.setDoseNumber(rs.getInt("dose_number"));
            vaccination.setVaccineLot(rs.getString("vaccine_lot"));
            vaccination.setConsentVerified(rs.getBoolean("consent_verified"));
            return vaccination;
        });
    }

    @Override
    public List<Student> getStudentsByParentUserId(int parentUserId) {
        String sql = """
        SELECT
            s.student_id,
            s.name,
            s.date_of_birth,
            s.gender,
            s.grade,
            s.class_id,
            ps.relationship AS rel
            FROM parentstudent ps
            JOIN student s ON ps.student_id = s.student_id
            WHERE ps.parent_user_id = ?
        """;

        return jdbcTemplate.query(sql, new Object[]{parentUserId}, (rs, rowNum) -> {
            Student student = new Student();
            student.setId(rs.getInt("student_id"));
            student.setName(rs.getString("name"));
            student.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
            student.setGender(rs.getString("gender"));
            student.setGrade(rs.getString("grade"));
            student.setClassId(rs.getInt("class_id"));
            student.setRelationship(rs.getString("rel"));
            return student;
        });
    }

    @Override
    public Optional<Appuser> findParentEmailByStudentId(int studentId) {
        String sql = """
        SELECT a.email
        FROM appuser a
        JOIN parentstudent ps ON a.user_id = ps.parent_user_id
        WHERE ps.student_id = ?
    """;

        // Sử dụng query để lấy kết quả và kiểm tra
        List<Appuser> parents = jdbcTemplate.query(sql, new Object[]{studentId}, (rs, rowNum) -> {
            Appuser parent = new Appuser();
            parent.setEmail(rs.getString("email"));
            return parent;
        });

        // Nếu tìm thấy phụ huynh, trả về Optional chứa đối tượng, nếu không trả về Optional.empty()
        return parents.isEmpty() ? Optional.empty() : Optional.of(parents.get(0));
    }
}
