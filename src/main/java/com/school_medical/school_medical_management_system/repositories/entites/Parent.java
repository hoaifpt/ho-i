package com.school_medical.school_medical_management_system.repositories.entites;

public class Parent {
    private Integer parentId;  // ID của phụ huynh trong bảng
    private Integer userId;    // ID của người dùng (Appuser) liên kết với phụ huynh này
    private String fullName;   // Họ và tên của phụ huynh
    private String gender;     // Giới tính của phụ huynh
    private String phone;      // Số điện thoại của phụ huynh

    // Getters và Setters
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
