package com.school_medical.school_medical_management_system.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.school_medical.school_medical_management_system.models.VaccinationParentDeclarationDTO;
import com.school_medical.school_medical_management_system.repositories.IVaccinationParentDeclarationRepository;
import com.school_medical.school_medical_management_system.services.IVaccinationParentDeclarationService;

@Service
public class VaccinationParentDeclarationServiceImpl implements IVaccinationParentDeclarationService {

    @Autowired
    private IVaccinationParentDeclarationRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<VaccinationParentDeclarationDTO> getAllByStudentId(int studentId) {
        return repository.getAllByStudentId(studentId);
    }

    @Override
    public void save(VaccinationParentDeclarationDTO dto) {
        try {
            // Lấy parent_id từ user_id
            Integer parentId = jdbcTemplate.queryForObject(
                    "SELECT parent_id FROM parent WHERE user_id = ?",
                    Integer.class,
                    dto.getParentId()  // Giả sử bạn truyền user_id từ người đăng nhập
            );

            // Gán vào DTO
            dto.setParentId(parentId);

            // Gọi xuống repository để lưu
            repository.save(dto);

        } catch (Exception e) {
            throw new RuntimeException("Error saving vaccination declaration: " + e.getMessage(), e);
        }
    }

    @   Override
public void deleteById(int id) {
    repository.deleteById(id);
}

    @Override
    public void update(VaccinationParentDeclarationDTO dto) {
        try {
            // Cập nhật thông tin vaccine
            repository.update(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error updating vaccination declaration: " + e.getMessage(), e);
        }
    }


}