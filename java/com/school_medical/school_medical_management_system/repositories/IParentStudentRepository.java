package com.school_medical.school_medical_management_system.repositories;

public interface IParentStudentRepository {
    boolean isStudentBelongsToParent(int parentId, int studentId);
}
