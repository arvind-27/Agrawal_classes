package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Guardian;

public interface GuardianDao {
    public void save(Guardian guardian);

    public String getNameByStudentId(int studentId);

    public void update(Guardian guardian);
}
