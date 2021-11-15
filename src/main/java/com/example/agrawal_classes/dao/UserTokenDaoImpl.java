package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UserTokenDaoImpl implements UserTokenDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public void save(UserToken userToken) {
        String sql = "INSERT INTO UserToken (token, userId) VALUES (?, ?)";
        template.update(sql, userToken.getToken(), userToken.getUserId());
    }

    @Override
    public Integer getUserIdByToken(String token) {
        try {
            String sql = "SELECT userId FROM UserToken WHERE token = ?";
            int userId = template.queryForObject(sql, new Object[] { token }, Integer.class);
            return userId;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public String getTokenByUserId(int userId) {
        try {
            String sql = "SELECT token FROM UserToken WHERE userId = ?";
            String token = template.queryForObject(sql, new Object[] { userId }, String.class);
            return token;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void update(UserToken userToken) {
        String sql = "UPDATE UserToken SET token = ? WHERE userId = ?";
        template.update(sql, userToken.getToken(), userToken.getUserId());
    }

    @Override
    public void delete(String token) {
        String sql = "DELETE FROM UserToken WHERE token = ?";
        template.update(sql, token);
    }

}
