package com.example.agrawal_classes.services;

import com.example.agrawal_classes.model.User;

public interface SecurityService {
    public String findLoggedInUsername();

    public int findLoggedInUserId();

    public String findLoggedInName();

    public String findLoggedInUserRole();

    public User findLoggedInUser();

    public void autoLogin(String username, String password);

    public void autoLogout();

    public boolean isUserDeletedOrUpdated();
}
