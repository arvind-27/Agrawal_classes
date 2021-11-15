package com.example.agrawal_classes.model;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import java.sql.Time;

public class Feedback {
    private int feedbackId;
    private int studentId;
    private int employeeId;
    private Date date;
    private Time time;

    @NotEmpty
    private String subject;

    @NotEmpty
    private String message;

    private String response;

    public Feedback() {
    }

    public Feedback(int feedbackId, int studentId, int employeeId, Date date, Time time, String subject, String message, String response) {
        this.feedbackId = feedbackId;
        this.studentId = studentId;
        this.employeeId = employeeId;
        this.date = date;
        this.time = time;
        this.subject = subject;
        this.message = message;
        this.response = response;
    }

    /**
     * @return int return the feedbackId
     */
    public int getFeedbackId() {
        return feedbackId;
    }

    /**
     * @param feedbackId the feedbackId to set
     */
    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
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
     * @return int return the employeeId
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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
     * @return String return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
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

    @Override
    public String toString() {
        return "{" +
            " feedbackId='" + getFeedbackId() + "'" +
            ", studentId='" + getStudentId() + "'" +
            ", employeeId='" + getEmployeeId() + "'" +
            ", date='" + getDate() + "'" +
            ", time='" + getTime() + "'" +
            ", subject='" + getSubject() + "'" +
            ", message='" + getMessage() + "'" +
            ", response='" + getResponse() + "'" +
            "}";
    }

}
