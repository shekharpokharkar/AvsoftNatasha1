package com.example.avsoft.dtos;

import java.util.HashMap;

public class AdminDashboardDto {
    private long enquiries;
    private long student;
    private HashMap<String, Integer> enquiryOverTime;

    // Constructors
    public AdminDashboardDto() {}

    public AdminDashboardDto(long enquiries, long student, HashMap<String, Integer> enquiryOverTime) {
        this.enquiries = enquiries;
        this.student = student;
        this.enquiryOverTime = enquiryOverTime;
    }

    // Getters and Setters
    public long getEnquiries() {
        return enquiries;
    }

    public void setEnquiries(long enquiries) {
        this.enquiries = enquiries;
    }

    public long getStudent() {
        return student;
    }

    public void setStudent(long student) {
        this.student = student;
    }

    public HashMap<String, Integer> getEnquiryOverTime() {
        return enquiryOverTime;
    }

    public void setEnquiryOverTime(HashMap<String, Integer> enquiryOverTime) {
        this.enquiryOverTime = enquiryOverTime;
    }
}
