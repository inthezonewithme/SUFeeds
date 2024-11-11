package com.example.kamwaro;

public class ClassModel {
    private String className;
    private String classCode;

    // Constructor
    public ClassModel(String className, String classCode) {
        this.className = className;
        this.classCode = classCode;
    }

    // Getters and Setters
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    // Optional: Override toString() for easier debugging
    @Override
    public String toString() {
        return "ClassModel{className='" + className + "', classCode='" + classCode + "'}";
    }
}
