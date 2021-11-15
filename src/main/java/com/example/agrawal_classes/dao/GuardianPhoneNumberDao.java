package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.GuardianPhoneNumber;

import java.util.List;

public interface GuardianPhoneNumberDao {
    public void save(GuardianPhoneNumber guardianPhoneNumber);

    public List<GuardianPhoneNumber> getByStudentId(int studentId);

    public void update(GuardianPhoneNumber guardianPhoneNumber, int oldPhoneNumber);

    public void delete(GuardianPhoneNumber guardianPhoneNumber);
}
