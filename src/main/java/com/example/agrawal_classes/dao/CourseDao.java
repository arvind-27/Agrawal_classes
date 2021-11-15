package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Course;

import java.util.List;
import java.util.Map;

public interface CourseDao {
    public void save(Course course);

    public List<Course> getAll();

    public List<Map<String, Object>> getAllList();

    public Course get(String courseId);

    public List<String> getCourseIdByStudentId(int studentId);

    public void update(Course course);

    public void delete(String courseId);
}
