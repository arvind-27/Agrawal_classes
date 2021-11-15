package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.UserToken;

public interface UserTokenDao {
    public void save(UserToken userToken);

    public Integer getUserIdByToken(String token);

    public String getTokenByUserId(int userId);

    public void update(UserToken userToken);

    public void delete(String token);
}
