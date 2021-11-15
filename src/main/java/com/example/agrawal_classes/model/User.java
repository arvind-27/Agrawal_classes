package com.example.agrawal_classes.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class User implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int userId;

    @NotEmpty
    private String username;

    private String password;

    @NotEmpty
    private String firstName;

    private String middleName;

    private String lastName;

    @NotEmpty
    @Email
    private String emailAddress;

    private Date dateCreated;
    private boolean isActive;
    private boolean isEmailVerified;
    private Date lastLoginDate;
    private Time lastLoginTime;
    private String role;

    public User() {
        isActive = false;
        isEmailVerified = false;
    }

    public User(int userId, String username, String password, String firstName, String middleName, String lastName, String emailAddress, Date dateCreated, boolean isActive, boolean isEmailVerified, Date lastLoginDate, Time lastLoginTime, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.dateCreated = dateCreated;
        this.isActive = isActive;
        this.isEmailVerified = isEmailVerified;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginTime = lastLoginTime;
        this.role = role;
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
     * @return String return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return String return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return firstName + " " + middleName + " " + lastName;
    }

    /**
     * @return String return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return String return the middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return String return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return String return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return Date return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return boolean return the isActive
     */
    public boolean isIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return boolean return the isEmailVerified
     */
    public boolean isIsEmailVerified() {
        return isEmailVerified;
    }

    /**
     * @param isEmailVerified the isEmailVerified to set
     */
    public void setIsEmailVerified(boolean isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    /**
     * @return Date return the lastLoginDate
     */
    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    /**
     * @param lastLoginDate the lastLoginDate to set
     */
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    /**
     * @return Time return the lastLoginTime
     */
    public Time getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * @param lastLoginTime the lastLoginTime to set
     */
    public void setLastLoginTime(Time lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * @return String return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @return String return only the role in smallcase
     */
    public String getSmallRole() {
        // Convert ROLE_ABC to abc
        return role.substring(5).toLowerCase();
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "{" +
                " userId='" + getUserId() + "'" +
                ", username='" + getUsername() + "'" +
                ", password='" + getPassword() + "'" +
                ", firstName='" + getFirstName() + "'" +
                ", middleName='" + getMiddleName() + "'" +
                ", lastName='" + getLastName() + "'" +
                ", emailAddress='" + getEmailAddress() + "'" +
                ", dateCreated='" + getDateCreated() + "'" +
                ", isActive='" + isIsActive() + "'" +
                ", lastLoginDate='" + getLastLoginDate() + "'" +
                ", lastLoginTime='" + getLastLoginTime() + "'" +
                ", role='" + getRole() + "'" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return userId == user.userId && Objects.equals(username, user.username)
                && Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName)
                && Objects.equals(middleName, user.middleName) && Objects.equals(lastName, user.lastName)
                && Objects.equals(emailAddress, user.emailAddress) && Objects.equals(dateCreated, user.dateCreated)
                && isActive == user.isActive && isEmailVerified == user.isEmailVerified
                && Objects.equals(lastLoginDate, user.lastLoginDate)
                && Objects.equals(lastLoginTime, user.lastLoginTime) && Objects.equals(role, user.role);
    }

}
