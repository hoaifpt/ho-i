package com.school_medical.school_medical_management_system.services.impl;

import com.school_medical.school_medical_management_system.repositories.IMedicalEventSupplyRepository;
import com.school_medical.school_medical_management_system.repositories.IMedicalSupplyRepository;
import com.school_medical.school_medical_management_system.repositories.entites.MedicalEventSupply;
import com.school_medical.school_medical_management_system.repositories.entites.MedicalSupply;
import com.school_medical.school_medical_management_system.services.IMedicalEventSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalEventSupplyService implements IMedicalEventSupplyService {

    @Autowired
    private IMedicalEventSupplyRepository eventSupplyRepo;

    @Autowired
    private IMedicalSupplyRepository medicalSupplyRepo;

    @Override
    public void addSupplyToEvent(MedicalEventSupply supply) {
        if (supply == null || supply.getSupplyId() == null || supply.getQuantityUsed() == null) {
            throw new IllegalArgumentException("Invalid supply data");
        }

        MedicalSupply storeSupply = medicalSupplyRepo.findById(supply.getSupplyId());
        if (storeSupply == null) {
            throw new IllegalArgumentException("Supply not found");
        }

        if (storeSupply.getQuantity() < supply.getQuantityUsed()) {
            throw new IllegalArgumentException("Not enough supply in stock. Available: " + storeSupply.getQuantity());
        }

        storeSupply.setQuantity(storeSupply.getQuantity() - supply.getQuantityUsed());
        medicalSupplyRepo.updateSupply(storeSupply);

        eventSupplyRepo.addSupplyToEvent(supply);
    }

    @Override
    public void updateSupplyUsed(Integer eventSupplyId, Integer newQuantity) {
        MedicalEventSupply existing = eventSupplyRepo.findById(eventSupplyId);
        if (existing == null) {
            throw new IllegalArgumentException("Supply event not found");
        }

        int oldQuantity = existing.getQuantityUsed();
        int delta = newQuantity - oldQuantity;

        MedicalSupply storeSupply = medicalSupplyRepo.findById(existing.getSupplyId());
        if (storeSupply == null) {
            throw new IllegalArgumentException("Supply not found");
        }

        int updatedStock = storeSupply.getQuantity() - delta;
        if (updatedStock < 0) {
            throw new IllegalArgumentException("Not enough stock");
        }

        storeSupply.setQuantity(updatedStock);
        medicalSupplyRepo.updateSupply(storeSupply);

        eventSupplyRepo.updateQuantityUsed(eventSupplyId, newQuantity);
    }

    @Override
    public List<MedicalEventSupply> getSuppliesByEvent(Integer eventId) {
        if (eventId == null) {
            throw new IllegalArgumentException("Event ID cannot be null");
        }
        return eventSupplyRepo.getSuppliesByEventId(eventId);
    }

    @Override
    public List<MedicalEventSupply> getAllEventSupplies() {
        return eventSupplyRepo.getAllEventSupplies();
    }
}
