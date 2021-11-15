package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.User;

import java.util.List;

public interface UserDao
{
    public User save(User user);
    public List<User> getAll();
    public User get(int userId);
    public String getPassword(int userId);
    public boolean exists(String emailAddress);
    public User findByEmailAddress(String emailAddress);
    public User findByUsername(String username);
    public void activate(int userId);
    public void verifyEmail(int userId);
    public void changePassword(int userId, String password);
    public User setLoginTimestamp(User user);
    public void setRole(User user, String role);
    public void update(User user);
    public void delete(int userId);
}
