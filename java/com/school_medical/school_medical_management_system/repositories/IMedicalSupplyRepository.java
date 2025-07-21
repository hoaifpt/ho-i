package com.school_medical.school_medical_management_system.repositories;

import com.school_medical.school_medical_management_system.repositories.entites.MedicalSupply;
import java.util.List;

public interface IMedicalSupplyRepository {
    void createSupply(MedicalSupply supply);
    List<MedicalSupply> getAllSupplies();
    MedicalSupply findById(Integer id);
    void updateSupply(MedicalSupply supply);
    void deleteSupply(Integer id);
}
