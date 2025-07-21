package com.school_medical.school_medical_management_system.VNPAY;

public class CreatePaymentResponse {
    private String paymentUrl;

    public CreatePaymentResponse(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }
}
