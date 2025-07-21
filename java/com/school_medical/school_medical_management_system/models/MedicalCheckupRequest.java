package com.school_medical.school_medical_management_system.models;

import lombok.Data;
import java.util.Date;

@Data
public class MedicalCheckupRequest {
    private Date checkupDate;
    private String description;
    private String status;
    private Boolean needFollowUp;
    private Integer studentId;
    private Integer batchId;
}
