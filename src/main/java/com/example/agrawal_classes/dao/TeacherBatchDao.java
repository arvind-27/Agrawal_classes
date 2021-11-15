package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.TeacherBatchDetails;

import java.util.List;

public interface TeacherBatchDao {
    public void save(String teacherId, String batchId, String courseId);

    public List<TeacherBatchDetails> getAll();

    public void delete(String teacherId, String batchId, String courseId);
}
