package com.example.kamwaro;

public class FeedbackModel {
    private String topicName;
    private String comment;


    // Constructor
    public FeedbackModel(String topicName, String comment) {
        this.topicName = topicName;
        this.comment = comment;
    }

    // Getters and Setters
    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // Optional: Override toString() for easier debugging
    @Override
    public String toString() {
        return "FeedbackModel{topicName='" + topicName + "', comment='" + comment + "'}";
    }
}
