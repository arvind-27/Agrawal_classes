package com.example.agrawal_classes.model;

public class CourseSubjectDetails {
    private String courseId;
    private Subject subject;

    public CourseSubjectDetails() {
        subject = new Subject();
    }

    public CourseSubjectDetails(String courseId, Subject subject) {
        this.courseId = courseId;
        this.subject = subject;
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
     * @return Subject return the subject
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "{" +
            " courseId='" + getCourseId() + "'" +
            ", subject='" + getSubject() + "'" +
            "}";
    }

}
