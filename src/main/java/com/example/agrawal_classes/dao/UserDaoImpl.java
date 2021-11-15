package com.example.agrawal_classes.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import com.example.agrawal_classes.dao.UserDao;
import com.example.agrawal_classes.model.User;
import com.example.agrawal_classes.utils.DateTimeUtil;
import com.example.agrawal_classes.utils.PreparedStatementUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate template;

    @Autowired
    private DateTimeUtil dateTimeUtil;

    @Autowired
    private PreparedStatementUtil preparedStatementUtil;

    @Override
    public User save(User user) {
        String sql = "INSERT INTO User (username, password, firstName, middleName, lastName, emailAddress, dateCreated, isActive, role) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement preparedStatement = conn.prepareStatement(sql, new String[] { "studentId" });
                preparedStatementUtil.setParameters(preparedStatement, user.getUsername(), user.getPassword(), user.getFirstName(),
                        user.getMiddleName(), user.getLastName(), user.getEmailAddress(), dateTimeUtil.getCurrentDateTime("yyyy-MM-dd"),
                        user.isIsActive(), user.getRole());
                return preparedStatement;
            }
        }, keyHolder);
        int userId = keyHolder.getKey().intValue();
        user.setUserId(userId);
        return user;
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM User";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<>(User.class));
        return users;
    }

    @Override
    public User get(int userId) {
        try {
            String sql = "SELECT * FROM User WHERE userId = ?";
            return (User) template.queryForObject(sql, new Object[] { userId },
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public String getPassword(int userId) {
        try {
            String sql = "SELECT password FROM User WHERE userId = ?";
            return template.queryForObject(sql, new Object[] { userId }, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean exists(String emailAddress) {
        String sql = "SELECT COUNT(*) FROM User WHERE emailAddress = ?";
        int count = template.queryForObject(sql, Integer.class);
        return (count > 0);
    }

    @Override
    public User findByEmailAddress(String emailAddress) {
        try {
            String sql = "SELECT * FROM User WHERE emailAddress = ?";
            return (User) template.queryForObject(sql, new Object[] { emailAddress },
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User findByUsername(String username) {
        try {
            String sql = "SELECT * FROM User WHERE username = ?";
            return (User) template.queryForObject(sql, new Object[] { username },
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void activate(int userId) {
        String sql = "UPDATE User SET isActive = true WHERE userId = ?";
        template.update(sql, userId);
    }

    @Override
    public void verifyEmail(int userId) {
        String sql = "UPDATE User SET isEmailVerified = true WHERE userId = ?";
        template.update(sql, userId);
    }

    @Override
    public void changePassword(int userId, String password) {
        String sql = "UPDATE User SET password = ? WHERE userId = ?";
        template.update(sql, password, userId);
    }

    @Override
    public User setLoginTimestamp(User user) {
        String lastLoginDate = dateTimeUtil.getCurrentDateTime("yyyy-MM-dd");
        String lastLoginTime = dateTimeUtil.getCurrentDateTime("HH:mm:ss");
        String sql = "UPDATE User SET lastLoginDate = ?, lastLoginTime = ? WHERE userId = ?";
        template.update(sql, lastLoginDate, lastLoginTime, user.getUserId());
        user.setLastLoginDate(Date.valueOf(lastLoginDate));
        user.setLastLoginTime(Time.valueOf(lastLoginTime));
        return user;
    }

    @Override
    public void setRole(User user, String role) {
        String sql = "UPDATE User SET role = ? WHERE userId = ?";
        template.update(sql, role, user.getUserId());
    }

    @Override
    /**
     * Update all attributes except userId, password, dateCreated, lastLoginDate,
     * lastLoginTime, role
     */
    public void update(User user) {
        String sql = "UPDATE User SET username = ?, firstName = ?, middleName = ?, lastName = ?, emailAddress = ?, "
                + "isActive = ? WHERE userId = ?";
        template.update(sql, user.getUsername(), user.getFirstName(), user.getMiddleName(), user.getLastName(),
                user.getEmailAddress(), user.isIsActive(), user.getUserId());
    }

    @Override
    public void delete(int userId) {
        String sql = "DELETE FROM User WHERE userId = ?";
        template.update(sql, userId);
    }

}
