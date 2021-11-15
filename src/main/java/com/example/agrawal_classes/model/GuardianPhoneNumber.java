package com.example.agrawal_classes.model;

public class GuardianPhoneNumber {
    private String phoneNumber;
    private String name;
    private int studentId;

    public GuardianPhoneNumber() {
    }

    public GuardianPhoneNumber(String phoneNumber, String name, int studentId) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.studentId = studentId;
    }

    /**
     * @return String return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return int return the studentId
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "{" +
            " phoneNumber='" + getPhoneNumber() + "'" +
            ", name='" + getName() + "'" +
            ", studentId='" + getStudentId() + "'" +
            "}";
    }

}
