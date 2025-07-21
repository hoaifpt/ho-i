package com.school_medical.school_medical_management_system.repositories;

import com.school_medical.school_medical_management_system.repositories.entites.MedicalEventSupply;

import java.util.List;

public interface IMedicalEventSupplyRepository {
    void addSupplyToEvent(MedicalEventSupply eventSupply);
    List<MedicalEventSupply> getSuppliesByEventId(Integer eventId);
    void updateQuantityUsed(Integer id, Integer newQuantity);
    MedicalEventSupply findById(Integer eventSupplyId);
    List<MedicalEventSupply> getAllEventSupplies();
}

