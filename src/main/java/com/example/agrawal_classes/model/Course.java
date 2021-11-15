package com.example.agrawal_classes.model;

import javax.validation.constraints.NotEmpty;

public class Course {
    @NotEmpty
    private String courseId;

    @NotEmpty
    private String courseName;

    private String description;

    public Course() {
    }

    public Course(String courseId, String courseName, String description) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.description = description;
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
     * @return String return the courseName
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * @param courseName the courseName to set
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    @Override
    public String toString() {
        return "{" +
            " courseId='" + getCourseId() + "'" +
            ", courseName='" + getCourseName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }

}
