package com.school_medical.school_medical_management_system.repositories;

import com.school_medical.school_medical_management_system.models.ParentInfoDTO;

public interface IParentInfoRepository {
    ParentInfoDTO getParentInfoByUserId(int userId);
}
