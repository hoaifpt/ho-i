package com.school_medical.school_medical_management_system.repositories.entites;

import java.util.Date;

public class Report {
    private int reportId;
    private Date reportDate;
    private String description;
    private String resultExpected;  // Expecting Result Column
    private String fileAttachment;  // For file attachment
    private String errorType;  // Added for tracking error types
    private int userId;
    private String status;  // Added for report status
    private Date createdAt;  // No change here

    // Getters and Setters
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResultExpected() {
        return resultExpected;
    }

    public void setResultExpected(String resultExpected) {
        this.resultExpected = resultExpected;
    }

    public String getFileAttachment() {
        return fileAttachment;
    }

    public void setFileAttachment(String fileAttachment) {
        this.fileAttachment = fileAttachment;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
