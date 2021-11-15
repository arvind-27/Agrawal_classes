package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.dao.rowmappers.StaffRowMapper;
import com.example.agrawal_classes.model.Staff;
import com.example.agrawal_classes.utils.PreparedStatementUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Transactional
@Repository
public class StaffDaoImpl implements StaffDao {
    @Autowired
    private JdbcTemplate template;

    @Autowired
    private PreparedStatementUtil preparedStatementUtil;

    @Override
    public Staff save(Staff staff) {
        String sql = "INSERT INTO Staff (gender, dateOfBirth, houseNumber, street, city, state, employeeId) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement preparedStatement = conn.prepareStatement(sql, new String[] {"staffId"});
                preparedStatementUtil.setParameters(preparedStatement, staff.getGender(), staff.getDateOfBirth(),
                        staff.getHouseNumber(), staff.getStreet(), staff.getCity(), staff.getState(),
                        staff.getEmployee().getEmployeeId());
                return preparedStatement;
            }
        }, keyHolder);
        int staffId = keyHolder.getKey().intValue();
        staff.setStaffId(staffId);
        return staff;
    }

    @Override
    public List<Staff> getAll() {
        String sql = "SELECT * FROM Staff NATURAL JOIN Employee NATURAL JOIN User";
        List<Staff> staffs = template.query(sql, new StaffRowMapper());
        return staffs;
    }

    @Override
    public List<Staff> getAllByBatch(String batchId, String courseId) {
        String sql = "SELECT * FROM Staff NATURAL JOIN StaffBatchDetails NATURAL JOIN Employee NATURAL JOIN User WHERE batchId = ? AND courseId = ?";
        List<Staff> teachers = template.query(sql, new Object[] { batchId, courseId }, new StaffRowMapper());
        return teachers;
    }

    @Override
    public List<Staff> getStaffsInBatch(String batchId, String courseId) {
        String sql = "SELECT * FROM Staff NATURAL JOIN Employee NATURAL JOIN User NATURAL JOIN StaffBatchDetails WHERE batchId = ? AND courseId = ?";
        List<Staff> subjects = template.query(sql, new Object[] { batchId, courseId },
                new StaffRowMapper());
        return subjects;
    }

    @Override
    public List<Staff> getStaffsNotInBatch(String batchId, String courseId) {
        String sql = "SELECT * FROM Staff NATURAL JOIN Employee NATURAL JOIN User WHERE staffId NOT IN (SELECT staffId FROM StaffBatchDetails WHERE batchId = ? AND courseId = ?)";
        List<Staff> subjects = template.query(sql, new Object[] { batchId, courseId },
                new StaffRowMapper());
        return subjects;
    }

    @Override
    public Staff getByEmployeeId(int employeeId) {
        try {
            String sql = "SELECT * FROM Staff NATURAL JOIN Employee NATURAL JOIN User WHERE employeeId = ?";
            return (Staff) template.queryForObject(sql, new Object[] { employeeId }, new StaffRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Staff getByUserId(int userId) {
        try {
            String sql = "SELECT * FROM Staff NATURAL JOIN Employee NATURAL JOIN User WHERE userId = ?";
            return (Staff) template.queryForObject(sql, new Object[] { userId }, new StaffRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer getStaffIdByUserId(int userId) {
        try {
            String sql = "SELECT staffId FROM Staff NATURAL JOIN Employee NATURAL JOIN User WHERE userId = ?";
            return template.queryForObject(sql, new Object[] { userId }, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Update all attributes except staffId and employeeId
     */
    @Override
    public void update(Staff staff) {
        String sql = "UPDATE Staff SET gender = ?, dateOfBirth = ?, houseNumber = ?, street = ?, city = ?, state = ? "
                + "WHERE staffId = ?";
        template.update(sql, staff.getGender(), staff.getDateOfBirth(), staff.getHouseNumber(),
                staff.getStreet(), staff.getCity(), staff.getState(), staff.getStaffId());
    }

    @Override
    public void delete(int staffId) {
        String sql = "DELETE FROM Staff WHERE staffId = ?";
        template.update(sql, staffId);
    }

}
