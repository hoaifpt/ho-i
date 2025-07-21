package com.school_medical.school_medical_management_system.services.impl;

import com.school_medical.school_medical_management_system.models.ParentInfoDTO;
import com.school_medical.school_medical_management_system.repositories.IParentInfoRepository;
import com.school_medical.school_medical_management_system.services.IParentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParentInfoServiceImpl implements IParentInfoService {

    @Autowired
    private IParentInfoRepository repository;

    @Override
    public ParentInfoDTO getParentInfoByUserId(int userId) {
        return repository.getParentInfoByUserId(userId);
    }
}

