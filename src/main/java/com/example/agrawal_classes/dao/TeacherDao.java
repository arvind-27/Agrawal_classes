package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Teacher;

import java.util.List;

public interface TeacherDao {
    public Teacher save(Teacher teacher);

    public List<Teacher> getAll();

    public List<Teacher> getAllByBatch(String batchId, String courseId);

    public List<Teacher> getTeachersInBatch(String batchId, String courseId);

    public List<Teacher> getTeachersNotInBatch(String batchId, String courseId);

    public Teacher getByEmployeeId(int employeeId);

    public Teacher getByUserId(int userId);

    public Integer getTeacherIdByUserId(int userId);

    public void update(Teacher teacher);

    public void delete(int teacherId);
}
