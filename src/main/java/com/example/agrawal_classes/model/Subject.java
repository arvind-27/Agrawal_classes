package com.example.agrawal_classes.model;

import javax.validation.constraints.NotBlank;

public class Subject {
    @NotBlank
    private String subjectId;

    @NotBlank
    private String subjectName;

    private String description;

    public Subject() {
    }

    public Subject(String subjectId, String subjectName, String description) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.description = description;
    }

    /**
     * @return String return the subjectId
     */
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * @param subjectId the subjectId to set
     */
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * @return String return the subjectName
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * @param subjectName the subjectName to set
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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
            " subjectId='" + getSubjectId() + "'" +
            ", subjectName='" + getSubjectName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }

}
