package com.school_medical.school_medical_management_system.repositories.entites;

import java.time.LocalDate;

public class VaccinationParentDeclaration {

    private Integer id;
    private Integer studentId;
    private Integer parentId;
    private String vaccinationName;
    private Integer doseNumber;
    private LocalDate declaredDate;
    private String notes;
    private String status;
    private String vaccineLot;
    private Boolean consentVerified;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getVaccinationName() {
        return vaccinationName;
    }

    public void setVaccinationName(String vaccinationName) {
        this.vaccinationName = vaccinationName;
    }

    public Integer getDoseNumber() {
        return doseNumber;
    }

    public void setDoseNumber(Integer doseNumber) {
        this.doseNumber = doseNumber;
    }

    public LocalDate getDeclaredDate() {
        return declaredDate;
    }

    public void setDeclaredDate(LocalDate declaredDate) {
        this.declaredDate = declaredDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVaccineLot() {
        return vaccineLot;
    }

    public void setVaccineLot(String vaccineLot) {
        this.vaccineLot = vaccineLot;
    }

    public Boolean getConsentVerified() {
        return consentVerified;
    }

    public void setConsentVerified(Boolean consentVerified) {
        this.consentVerified = consentVerified;
    }
}
