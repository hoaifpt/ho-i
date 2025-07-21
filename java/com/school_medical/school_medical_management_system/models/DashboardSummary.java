package com.school_medical.school_medical_management_system.models;

public class DashboardSummary {
    private String reportMonth;
    private Long income;
    private int usersToday;
    private int newCustomers;
    private int newOrders;
    private int activeUsers;
    private int medicationSubmissions;
    private int totalUsers;
    private String reportTitle;

    // Getters & Setters
    public String getReportMonth() {
        return reportMonth;
    }

    public void setReportMonth(String reportMonth) {
        this.reportMonth = reportMonth;
    }

    public Long getIncome() {
        return income;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

    public int getUsersToday() {
        return usersToday;
    }

    public void setUsersToday(int usersToday) {
        this.usersToday = usersToday;
    }

    public int getNewCustomers() {
        return newCustomers;
    }

    public void setNewCustomers(int newCustomers) {
        this.newCustomers = newCustomers;
    }

    public int getNewOrders() {
        return newOrders;
    }

    public void setNewOrders(int newOrders) {
        this.newOrders = newOrders;
    }

    public int getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(int activeUsers) {
        this.activeUsers = activeUsers;
    }

    public int getMedicationSubmissions() {
        return medicationSubmissions;
    }

    public void setMedicationSubmissions(int medicationSubmissions) {
        this.medicationSubmissions = medicationSubmissions;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }
}
