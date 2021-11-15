package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Enrollment;

import java.util.List;

public interface EnrollmentDao {
    public void save(Enrollment enrollment);

    public List<Enrollment> getAll();

    public List<Enrollment> getAllByStudentId(int studentId);

    public List<Enrollment> getAllByCourseId(String courseId);

    public List<Enrollment> getAllByBatch(String courseId, String batchId);

    public Enrollment get(int enrollmentId);

    public Enrollment getByStudentAndCourse(int studentId, String courseId);

    public void update(Enrollment enrollment);

    public void delete(int enrollmentId);
}
