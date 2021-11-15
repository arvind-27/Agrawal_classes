package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Result;

import java.util.List;

public interface ResultDao {
    public void save(Result result);

    public List<Result> getAllByTestId(int testId);

    public List<Result> getAllRechecksByTestId(int testId);

    public Result get(int testId, int studentId);

    public int isStudentAppearedInTest(int testId, int studentId);

    public void applyForRecheck(int testId, int studentId, String recheckComments);

    public void updateMarksAndMarkDone(int testId, int studentId, int marks);

    public void updateMarks(Result result);

    public void update(Result result);

    public void delete(int testId, int studentId);
}
