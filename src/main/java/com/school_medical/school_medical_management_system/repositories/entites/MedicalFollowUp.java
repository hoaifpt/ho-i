package com.school_medical.school_medical_management_system.repositories.entites;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MedicalFollowUp {

    private Integer followupId;      // followup_id INT AUTO_INCREMENT PRIMARY KEY
    private Integer eventId;         // event_id INT (Foreign Key tới MedicalEvent)
    private Integer vaccinationId;   // vaccination_id INT (Foreign Key tới Vaccination)
    private LocalDate followupDate;  // followup_date DATE
    private String notes;            // notes TEXT
    private String status;           // status VARCHAR(50)
}