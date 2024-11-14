package com.example.kamwaro;

public class ReviewsModel {
    private String fname;
    private String feedback;

    // Constructor
    public ReviewsModel(String fullName, String feedback) {
        this.fname = fullName;
        this.feedback = feedback;
    }

    // Getters and Setters
    public String getFullName() {
        return fname;
    }

    public void setFullName(String fullName) {
        this.fname = fullName;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

}


