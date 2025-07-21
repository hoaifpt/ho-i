package com.school_medical.school_medical_management_system.repositories.entites;

import lombok.Data;
import java.util.Date;


public class Medicalcheckup {
    private Integer checkupId;
    private Date checkupDate;
    private String description;
    private String status;
    private Boolean needFollowUp;
    private Integer studentId;
    private Integer batchId;


    public Integer getCheckupId() {
        return checkupId;
    }

    public void setCheckupId(Integer checkupId) {
        this.checkupId = checkupId;
    }

    public Date getCheckupDate() {
        return checkupDate;
    }

    public void setCheckupDate(Date checkupDate) {
        this.checkupDate = checkupDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getNeedFollowUp() {
        return needFollowUp;
    }

    public void setNeedFollowUp(Boolean needFollowUp) {
        this.needFollowUp = needFollowUp;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }
}
