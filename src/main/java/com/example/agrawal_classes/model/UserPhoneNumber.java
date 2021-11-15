package com.example.agrawal_classes.model;

public class UserPhoneNumber {
    private String phoneNumber;
    private int userId;

    public UserPhoneNumber() {
    }

    public UserPhoneNumber(String phoneNumber, int userId) {
        this.phoneNumber = phoneNumber;
        this.userId = userId;
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
     * @return int return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "{" +
            " phoneNumber='" + getPhoneNumber() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }

}
