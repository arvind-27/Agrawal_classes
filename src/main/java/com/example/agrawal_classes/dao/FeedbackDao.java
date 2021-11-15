package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Feedback;

import java.util.List;

public interface FeedbackDao {
    public void save(Feedback feedback);

    public Feedback get(int feedbackId);

    public List<Feedback> getAll();

    public List<Feedback> getAllByStudentId(int studentId);

    public List<Feedback> getAllByEmployeeId(int employeeId);

    public void respond(int feedbackId, String response);

    public void update(Feedback feedback);

    public void delete(int feedbackId);
}
