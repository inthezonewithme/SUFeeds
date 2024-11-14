package com.example.kamwaro;

public class TopicModel {
    private String topicName;
    private String weekNumber;


    // Constructor
    public TopicModel(String topicName, String weekNumber) {
        this.topicName = topicName;
        this.weekNumber = weekNumber;
    }

    // Getters and Setters
    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(String weekNumber) {
        this.weekNumber = weekNumber;
    }

    // Optional: Override toString() for easier debugging
    @Override
    public String toString() {
        return "TopicModel{topicName='" + topicName + "', weekNumber='" + weekNumber + "'}";
    }
}
