package com.example.agrawal_classes.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.sql.Time;
import java.util.Objects;

public class Batch {
    @NotEmpty
    private String batchId;

    private Course course;

    @NotEmpty
    private String batchName;

    @Min(0)
    private int fee;

    @Positive
    private int roomNumber;

    private Time startTime;
    private Time endTime;

    public Batch() {
        course = new Course();
    }

    public Batch(String batchId, Course course, String batchName, int fee, int roomNumber, Time startTime, Time endTime) {
        this.batchId = batchId;
        this.course = course;
        this.batchName = batchName;
        this.fee = fee;
        this.roomNumber = roomNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * @return String return the batchId
     */
    public String getBatchId() {
        return batchId;
    }

    /**
     * @param batchId the batchId to set
     */
    public void setBatchId(String batchId) {
        this.batchId = batchId;
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

    /**
     * @return String return the batchName
     */
    public String getBatchName() {
        return batchName;
    }

    /**
     * @param batchName the batchName to set
     */
    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    /**
     * @return int return the fee
     */
    public int getFee() {
        return fee;
    }

    /**
     * @param fee the fee to set
     */
    public void setFee(int fee) {
        this.fee = fee;
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

    @Override
    public String toString() {
        return "{" +
            " batchId='" + getBatchId() + "'" +
            ", course='" + getCourse() + "'" +
            ", batchName='" + getBatchName() + "'" +
            ", fee='" + getFee() + "'" +
            ", roomNumber='" + getRoomNumber() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Batch)) {
            return false;
        }
        Batch batch = (Batch) o;
        return Objects.equals(batchId, batch.batchId) && Objects.equals(course.getCourseId(), batch.course.getCourseId());
    }

}
