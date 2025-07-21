package com.school_medical.school_medical_management_system.models;

public class ConsentResponse {
    private Boolean consentStatus; // TRUE: đồng ý, FALSE: từ chối

    public Boolean getConsentStatus() {
        return consentStatus;
    }

    public void setConsentStatus(Boolean consentStatus) {
        this.consentStatus = consentStatus;
    }
}
