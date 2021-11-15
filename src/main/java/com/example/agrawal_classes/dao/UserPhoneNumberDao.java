package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.UserPhoneNumber;

import java.util.List;

public interface UserPhoneNumberDao {
    public void save(UserPhoneNumber userPhoneNumber);

    public List<UserPhoneNumber> getByUserId(int userId);

    public void update(UserPhoneNumber userPhoneNumber, int oldPhoneNumber);

    public void delete(UserPhoneNumber userPhoneNumber);
}
