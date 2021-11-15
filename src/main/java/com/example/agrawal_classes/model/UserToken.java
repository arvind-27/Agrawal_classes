package com.example.agrawal_classes.model;

import java.util.UUID;

public class UserToken {
    private int userId;
    private String token;

    public UserToken() {
    }

    public UserToken(int userId) {
        this.userId = userId;
        this.token = UUID.randomUUID().toString();
    }

    public UserToken userId(int userId) {
        this.userId = userId;
        return this;
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

    /**
     * @return String return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "{" +
            " userId='" + getUserId() + "'" +
            ", token='" + getToken() + "'" +
            "}";
    }

}
