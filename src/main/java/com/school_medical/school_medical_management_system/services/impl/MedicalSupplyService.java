package com.school_medical.school_medical_management_system.services.impl;

import com.school_medical.school_medical_management_system.repositories.IMedicalSupplyRepository;
import com.school_medical.school_medical_management_system.repositories.entites.MedicalSupply;
import com.school_medical.school_medical_management_system.services.IMedicalSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalSupplyService implements IMedicalSupplyService {

    @Autowired
    private IMedicalSupplyRepository repository;

    @Override
    public void createSupply(MedicalSupply supply) {
        repository.createSupply(supply);
    }

    @Override
    public List<MedicalSupply> getAllSupplies() {
        return repository.getAllSupplies();
    }

    @Override
    public MedicalSupply findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public void updateSupply(MedicalSupply supply) {
        repository.updateSupply(supply);  // Gọi phương thức cập nhật trong repository
    }

    @Override
    public void deleteSupply(Integer id) {
        repository.deleteSupply(id);
    }
}
