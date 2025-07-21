package com.school_medical.school_medical_management_system.models;

import java.time.LocalDateTime;

public class ApprovalRequest {
    private String approvalStatus;  // e.g., "Approved" or "Rejected"
    private Integer approvedBy;

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Integer getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Integer approvedBy) {
        this.approvedBy = approvedBy;
    }
}