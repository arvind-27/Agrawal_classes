package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Subject;

import java.util.List;

public interface SubjectDao {
    public void save(Subject subject);

    public List<Subject> getAll();

    public Subject get(String subjectId);

    public Subject getBySubjectName(String subjectName);

    public List<Subject> getSubjectsInCourse(String courseId);

    public List<Subject> getSubjectsNotInCourse(String courseId);

    public List<String> getSubjectCodeByStudentId(int studentId);

    public String getSubjectCodeByTeacherId(int teacherId);

    public void update(Subject subject);

    public void delete(String subjectId);
}
