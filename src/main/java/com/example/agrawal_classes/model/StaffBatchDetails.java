package com.example.agrawal_classes.model;

public class StaffBatchDetails {
    private int staffId;
    private Batch batch;

    public StaffBatchDetails() {
        batch = new Batch();
    }

    public StaffBatchDetails(int staffId, Batch batch) {
        this.staffId = staffId;
        this.batch = batch;
    }

    /**
     * @return int return the staffId
     */
    public int getStaffId() {
        return staffId;
    }

    /**
     * @param staffId the staffId to set
     */
    public void setStaffId(int staffId) {
        this.staffId = staffId;
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
            " staffId='" + getStaffId() + "'" +
            ", batch='" + getBatch() + "'" +
            "}";
    }

}
