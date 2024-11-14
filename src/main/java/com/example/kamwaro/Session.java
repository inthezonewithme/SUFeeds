package com.example.kamwaro;

public class Session {
    private static Session instance;
    private String studentId;
    private String firstName;
    private String lastName;

    // Private constructor to enforce singleton pattern
    private Session() {}

    // Method to get the single instance of the Session class
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Method to clear session details when logging out
    public void clearSession() {
        this.studentId = null;
        this.firstName = null;
        this.lastName = null;
    }
}

