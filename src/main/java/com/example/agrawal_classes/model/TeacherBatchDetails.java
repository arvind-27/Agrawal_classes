package com.example.agrawal_classes.model;

public class TeacherBatchDetails {
    private int teacherId;
    private Batch batch;

    public TeacherBatchDetails() {
        batch = new Batch();
    }

    public TeacherBatchDetails(int teacherId, Batch batch) {
        this.teacherId = teacherId;
        this.batch = batch;
    }

    /**
     * @return int return the teacherId
     */
    public int getTeacherId() {
        return teacherId;
    }

    /**
     * @param teacherId the teacherId to set
     */
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * @return Batch return the batch
     */
    public Batch getBatch() {
        return batch;
    }

    /**
     * @param batch the batch to set
     */
    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    @Override
    public String toString() {
        return "{" +
            " teacherId='" + getTeacherId() + "'" +
            ", batch='" + getBatch() + "'" +
            "}";
    }

}
