package com.school_medical.school_medical_management_system.repositories.entites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

public class Parentconsent {
    private Integer id;
    private Appuser parentUser;

    private Student student;

    private String consentType;

    private LocalDate consentDate;

    private Boolean status;

    public LocalDate getConsentDate() {
        return consentDate;
    }

    public void setConsentDate(LocalDate consentDate) {
        this.consentDate = consentDate;
    }

    public String getConsentType() {
        return consentType;
    }

    public void setConsentType(String consentType) {
        this.consentType = consentType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Appuser getParentUser() {
        return parentUser;
    }

    public void setParentUser(Appuser parentUser) {
        this.parentUser = parentUser;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}