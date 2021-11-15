package com.example.agrawal_classes.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.Date;
import java.sql.Time;

public class Test {
    private int testId;

    @NotEmpty
    private String testName;

    @Positive
    private int roomNumber;

    @NotNull
    private Date testDate;

    @NotNull
    private Time startTime;

    @NotNull
    private Time endTime;

    @Positive
    private int maximumMarks;

    @NotEmpty
    private String difficulty;

    private Course course;

    public Test() {
    }

    public Test(int testId, String testName, int roomNumber, Date testDate, Time startTime, Time endTime, int maximumMarks, String difficulty, Course course) {
        this.testId = testId;
        this.testName = testName;
        this.roomNumber = roomNumber;
        this.testDate = testDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maximumMarks = maximumMarks;
        this.difficulty = difficulty;
        this.course = course;
    }

    /**
     * @return int return the testId
     */
    public int getTestId() {
        return testId;
    }

    /**
     * @param testId the testId to set
     */
    public void setTestId(int testId) {
        this.testId = testId;
    }

    /**
     * @return String return the testName
     */
    public String getTestName() {
        return testName;
    }

    /**
     * @param testName the testName to set
     */
    public void setTestName(String testName) {
        this.testName = testName;
    }

    /**
     * @return int return the roomNumber
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * @param roomNumber the roomNumber to set
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * @return Date return the testDate
     */
    public Date getTestDate() {
        return testDate;
    }

    /**
     * @param testDate the testDate to set
     */
    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    /**
     * @return Time return the startTime
     */
    public Time getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    /**
     * @return Time return the endTime
     */
    public Time getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    /**
     * @return int return the maximumMarks
     */
    public int getMaximumMarks() {
        return maximumMarks;
    }

    /**
     * @param maximumMarks the maximumMarks to set
     */
    public void setMaximumMarks(int maximumMarks) {
        this.maximumMarks = maximumMarks;
    }

    /**
     * @return String return the difficulty
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * @param difficulty the difficulty to set
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * @return Course return the course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * @param course the course to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "{" +
            " testId='" + getTestId() + "'" +
            ", testName='" + getTestName() + "'" +
            ", roomNumber='" + getRoomNumber() + "'" +
            ", testDate='" + getTestDate() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", maximumMarks='" + getMaximumMarks() + "'" +
            ", difficulty='" + getDifficulty() + "'" +
            ", course='" + getCourse() + "'" +
            "}";
    }

}
