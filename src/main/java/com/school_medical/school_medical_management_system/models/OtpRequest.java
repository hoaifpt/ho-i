package com.school_medical.school_medical_management_system.models;

public class OtpRequest {
    private String accountNumber;

    public OtpRequest() {
    }

    public OtpRequest(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "OtpRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
