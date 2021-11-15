package com.example.agrawal_classes.model;

import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Time;

public class Complaint {
    private int complaintId;
    private Date date;
    private Time time;

    @NotBlank
    private String subject;

    @NotBlank
    private String description;

    private String response;
    private boolean isResolved;
    private int studentId;

    public Complaint() {
    }

    public Complaint(int complaintId, Date date, Time time, String subject, String description, String response, boolean isResolved, int studentId) {
        this.complaintId = complaintId;
        this.date = date;
        this.time = time;
        this.subject = subject;
        this.description = description;
        this.response = response;
        this.isResolved = isResolved;
        this.studentId = studentId;
    }

    /**
     * @return int return the complaintId
     */
    public int getComplaintId() {
        return complaintId;
    }

    /**
     * @param complaintId the complaintId to set
     */
    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    /**
     * @return Date return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return Time return the time
     */
    public Time getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Time time) {
        this.time = time;
    }

    /**
     * @return String return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return String return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return String return the response
     */
    public String getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * @return boolean return the isResolved
     */
    public boolean isIsResolved() {
        return isResolved;
    }

    /**
     * @param isResolved the isResolved to set
     */
    public void setIsResolved(boolean isResolved) {
        this.isResolved = isResolved;
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
            " complaintId='" + getComplaintId() + "'" +
            ", date='" + getDate() + "'" +
            ", time='" + getTime() + "'" +
            ", subject='" + getSubject() + "'" +
            ", description='" + getDescription() + "'" +
            ", response='" + getResponse() + "'" +
            ", isResolved='" + isIsResolved() + "'" +
            ", studentId='" + getStudentId() + "'" +
            "}";
    }

}
