package com.example.agrawal_classes.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.sql.Date;

public class Student {
    private int studentId;

    @NotBlank
    private String gender;

    private Date dateOfBirth;

    @NotBlank
    private String houseNumber;

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String schoolAttending;

    private User user;

    @Valid
    private Guardian guardian;

    public Student() {
        user = new User();
        guardian = new Guardian();
    }

    public Student(int studentId, String gender, Date dateOfBirth, String houseNumber, String street, String city, String state, String schoolAttending, User user, Guardian guardian) {
        this.studentId = studentId;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.houseNumber = houseNumber;
        this.street = street;
        this.city = city;
        this.state = state;
        this.schoolAttending = schoolAttending;
        this.user = user;
        this.guardian = guardian;
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

    /**
     * @return String return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return Date return the dateOfBirth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth the dateOfBirth to set
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return String return the houseNumber
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * @param houseNumber the houseNumber to set
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * @return String return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return String return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return String return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return String return the schoolAttending
     */
    public String getSchoolAttending() {
        return schoolAttending;
    }

    /**
     * @param schoolAttending the schoolAttending to set
     */
    public void setSchoolAttending(String schoolAttending) {
        this.schoolAttending = schoolAttending;
    }

    /**
     * @return User return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return Guardian return the guardian
     */
    public Guardian getGuardian() {
        return guardian;
    }

    /**
     * @param guardian the guardian to set
     */
    public void setGuardian(Guardian guardian) {
        this.guardian = guardian;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", houseNumber='" + houseNumber + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", schoolAttending='" + schoolAttending + '\'' +
                ", user=" + user +
                ", guardian=" + guardian +
                '}';
    }
}
