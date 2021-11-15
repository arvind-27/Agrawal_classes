package com.example.agrawal_classes.services;

import com.example.agrawal_classes.model.User;

public interface UserService {
    public User save(User user);

    public User findByEmailAddress(String emailAddress);

    public User findByUsername(String username);

    public User activateUserAndEmailToken(User user);

    public void sendVerificationEmail(User user);

    public void sendPasswordResetEmail(User user);

    public void verifyEmail(String token);

    public void confirmRegistration(String token, String password);

    public void resetPassword(String token, String password);

    public void changePassword(int userId, String password);

    public boolean verifyOldPassword(int userId, String password);
}
