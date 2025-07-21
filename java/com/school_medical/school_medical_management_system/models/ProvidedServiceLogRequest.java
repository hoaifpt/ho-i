package com.school_medical.school_medical_management_system.models;

public class ProvidedServiceLogRequest {
    private String nameType;
    private Integer studentId;
    private Integer nurseId;

    // Constructors
    public ProvidedServiceLogRequest() {}

    public ProvidedServiceLogRequest(String nameType, Integer studentId, Integer nurseId) {
        this.nameType = nameType;
        this.studentId = studentId;
        this.nurseId = nurseId;
    }

    // Getters and Setters
    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getNurseId() {
        return nurseId;
    }

    public void setNurseId(Integer nurseId) {
        this.nurseId = nurseId;
    }
}
