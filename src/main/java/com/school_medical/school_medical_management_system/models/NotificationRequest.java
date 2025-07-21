package com.school_medical.school_medical_management_system.models;

public class NotificationRequest {
    private String content;
    private String type;
    private String consentType;

    // Constructors
    public NotificationRequest() {}

    public NotificationRequest(String content, String type, String consentType) {
        this.content = content;
        this.type = type;
        this.consentType = consentType;
    }

    // Getters and Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConsentType() {
        return consentType;
    }

    public void setConsentType(String consentType) {
        this.consentType = consentType;
    }
}
