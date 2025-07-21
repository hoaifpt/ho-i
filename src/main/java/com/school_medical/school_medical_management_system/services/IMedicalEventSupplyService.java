package com.school_medical.school_medical_management_system.services;

import com.school_medical.school_medical_management_system.repositories.entites.MedicalEventSupply;

import java.util.List;

public interface IMedicalEventSupplyService {
    void addSupplyToEvent(MedicalEventSupply supply);
    void updateSupplyUsed(Integer eventSupplyId, Integer newQuantity);
    List<MedicalEventSupply> getSuppliesByEvent(Integer eventId);
    List<MedicalEventSupply> getAllEventSupplies();
}
