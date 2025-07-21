package com.school_medical.school_medical_management_system.services;

import com.school_medical.school_medical_management_system.models.ParentInfoDTO;

public interface IParentInfoService {
    ParentInfoDTO getParentInfoByUserId(int userId);
}

