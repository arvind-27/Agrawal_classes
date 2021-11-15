package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.StaffBatchDetails;

import java.util.List;

public interface StaffBatchDao {
    public void save(String staffId, String batchId, String courseId);

    public List<StaffBatchDetails> getAll();

    public void delete(String staffId, String batchId, String courseId);
}
