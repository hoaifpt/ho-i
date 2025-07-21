package com.school_medical.school_medical_management_system.models;

import java.sql.Date;

public class VaccinationParentDeclarationDTO {
    private int id;
    private int studentId;
    private String studentName;
    private int parentId;
    private String parentName;
    private String vaccineName;
    private Date declaredDate;
    private String notes;
    private String status;
    private int doseNumber;
    private String vaccineLot;
    private boolean consentVerified;

    // Constructors
    public VaccinationParentDeclarationDTO() {}

    public VaccinationParentDeclarationDTO(int id, int studentId, String studentName, int parentId,
                                           String parentName, String vaccineName, Date declaredDate,
                                           String notes, String status, int doseNumber,
                                           String vaccineLot, boolean consentVerified) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.parentId = parentId;
        this.parentName = parentName;
        this.vaccineName = vaccineName;
        this.declaredDate = declaredDate;
        this.notes = notes;
        this.status = status;
        this.doseNumber = doseNumber;
        this.vaccineLot = vaccineLot;
        this.consentVerified = consentVerified;
    }

    // Getters and Setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }

    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }

    public void setStudentName(String studentName) { this.studentName = studentName; }

    public int getParentId() { return parentId; }

    public void setParentId(int parentId) { this.parentId = parentId; }

    public String getParentName() { return parentName; }

    public void setParentName(String parentName) { this.parentName = parentName; }

    public String getVaccineName() { return vaccineName; }

    public void setVaccineName(String vaccineName) { this.vaccineName = vaccineName; }

    public Date getDeclaredDate() { return declaredDate; }

    public void setDeclaredDate(Date declaredDate) { this.declaredDate = declaredDate; }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public int getDoseNumber() { return doseNumber; }

    public void setDoseNumber(int doseNumber) { this.doseNumber = doseNumber; }

    public String getVaccineLot() { return vaccineLot; }

    public void setVaccineLot(String vaccineLot) { this.vaccineLot = vaccineLot; }

    public boolean isConsentVerified() { return consentVerified; }

    public void setConsentVerified(boolean consentVerified) { this.consentVerified = consentVerified; }
}
