package com.example.agrawal_classes.model;

import javax.validation.constraints.NotBlank;

public class StudyMaterial {
    @NotBlank
    private String subjectId;

    @NotBlank
    private String materialId;

    @NotBlank
    private String topicName;

    @NotBlank
    private String difficulty;

    private String description;

    private String filename;

    public StudyMaterial() {
    }

    public StudyMaterial(String subjectId, String materialId, String topicName, String difficulty, String description, String filename) {
        this.subjectId = subjectId;
        this.materialId = materialId;
        this.topicName = topicName;
        this.difficulty = difficulty;
        this.description = description;
        this.filename = filename;
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
     * @return String return the materialId
     */
    public String getMaterialId() {
        return materialId;
    }

    /**
     * @param materialId the materialId to set
     */
    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    /**
     * @return String return the topicName
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * @param topicName the topicName to set
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName;
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
     * @return String return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "{" +
            " subjectId='" + getSubjectId() + "'" +
            ", materialId='" + getMaterialId() + "'" +
            ", topicName='" + getTopicName() + "'" +
            ", difficulty='" + getDifficulty() + "'" +
            ", description='" + getDescription() + "'" +
            ", filename='" + getFilename() + "'" +
            "}";
    }

}
