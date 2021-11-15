package com.example.agrawal_classes.model;

import java.sql.Date;

public class Enrollment {
    private int enrollmentId;
    private int studentId;
    private String batchId;
    private String courseId;
    private Transaction transaction;
    private Date joinDate;
    private Date endDate;

    public Enrollment() {
        transaction = new Transaction();
    }

    public Enrollment(int enrollmentId, int studentId, String batchId, String courseId, Transaction transaction, Date joinDate, Date endDate) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.batchId = batchId;
        this.courseId = courseId;
        this.transaction = transaction;
        this.joinDate = joinDate;
        this.endDate = endDate;
    }

    /**
     * @return int return the enrollmentId
     */
    public int getEnrollmentId() {
        return enrollmentId;
    }

    /**
     * @param enrollmentId the enrollmentId to set
     */
    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
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
     * @return String return the courseId
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * @param courseId the courseId to set
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * @return Transaction return the transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * @param transaction the transaction to set
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    /**
     * @return Date return the joinDate
     */
    public Date getJoinDate() {
        return joinDate;
    }

    /**
     * @param joinDate the joinDate to set
     */
    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    /**
     * @return Date return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "{" +
            " enrollmentId='" + getEnrollmentId() + "'" +
            ", studentId='" + getStudentId() + "'" +
            ", batchId='" + getBatchId() + "'" +
            ", courseId='" + getCourseId() + "'" +
            ", transaction='" + getTransaction() + "'" +
            ", joinDate='" + getJoinDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }

}
