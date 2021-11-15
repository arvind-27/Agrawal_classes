package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.CourseSubjectDetails;

import java.util.List;

public interface CourseSubjectDao {
    public void save(String courseId, String subjectId);

    public List<CourseSubjectDetails> getAll();

    public void delete(String courseId, String subjectId);
}
