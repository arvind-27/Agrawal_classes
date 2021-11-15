package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Test;

import java.util.List;
import java.util.Map;

public interface TestDao {
    public void save(Test test);

    public List<Test> getAll();

    public List<Test> getAllByCourseId(String courseId);

    public List<Map<String, Object>> getAllByStudentId(int studentId);

    public Test get(int testId);

    public Integer getMaximumMarks(int testId);

    public void update(Test test);

    public void delete(int testId);
}
