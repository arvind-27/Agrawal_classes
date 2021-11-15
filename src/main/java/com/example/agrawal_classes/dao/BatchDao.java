package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Batch;

import java.util.List;

public interface BatchDao {
    public void save(Batch batch);

    public List<Batch> getAll();

    public List<Batch> getAllByCourseId(String courseId);

    public List<Batch> getAllByTeacherId(int teacherId);

    public List<Batch> getAllBySubjectId(String subjectId);

    public List<Batch> getAllByStaffId(int staffId);

    public Batch get(String batchId, String courseId);

    public int getFee(String batchId, String courseId);

    public void update(Batch batch);

    public void delete(String batchId, String courseId);
}
