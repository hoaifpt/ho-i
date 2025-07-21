package com.school_medical.school_medical_management_system.services;

import java.util.List;
import java.util.Map;

public interface IParentStudentService {
    boolean isStudentBelongsToParent(int parentId, int studentId);
    List<Integer> getStudentIdsByParentId(int parentUserId);
}
