package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.dao.rowmappers.EmployeeRowMapper;
import com.example.agrawal_classes.model.Employee;
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
public class EmployeeDaoImpl implements com.example.agrawal_classes.dao.EmployeeDao {
    @Autowired
    private JdbcTemplate template;

    @Autowired
    private PreparedStatementUtil preparedStatementUtil;

    @Override
    public Employee save(Employee employee) {
        String sql = "INSERT INTO Employee (basicSalary, joinDate, endDate, panNumber, accountNumber, userId) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement preparedStatement = conn.prepareStatement(sql, new String[] {"employeeId"});
                preparedStatementUtil.setParameters(preparedStatement, employee.getBasicSalary(), employee.getJoinDate(),
                        employee.getEndDate(), employee.getPanNumber(), employee.getAccountNumber(), employee.getUser().getUserId());
                return preparedStatement;
            }
        }, keyHolder);
        int employeeId = keyHolder.getKey().intValue();
        employee.setEmployeeId(employeeId);
        return employee;
    }

    @Override
    public List<Employee> getAll() {
        String sql = "SELECT * FROM Employee NATURAL JOIN User ORDER BY employeeId";
        List<Employee> employees = template.query(sql, new EmployeeRowMapper());
        return employees;
    }

    @Override
    public List<Employee> getAllTeachers() {
        String sql = "SELECT * FROM Employee NATURAL JOIN User WHERE role='ROLE_TEACHER'";
        List<Employee> employees = template.query(sql, new EmployeeRowMapper());
        return employees;
    }

    @Override
    public List<Employee> getAllStaffs() {
        String sql = "SELECT * FROM Employee NATURAL JOIN User WHERE role='ROLE_STAFF'";
        List<Employee> employees = template.query(sql, new EmployeeRowMapper());
        return employees;
    }

    @Override
    public Employee get(int employeeId) {
        try {
            String sql = "SELECT * FROM Employee NATURAL JOIN User WHERE employeeId = ?";
            return (Employee) template.queryForObject(sql, new Object[] { employeeId }, new EmployeeRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer getEmployeeIdByUserId(int userId) {
        try {
            String sql = "SELECT employeeId FROM Employee WHERE userId = ?";
            return template.queryForObject(sql, new Object[] { userId }, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public String getRole(int employeeId) {
        try {
            String sql = "SELECT role FROM Employee NATURAL JOIN User WHERE employeeId = ?";
            return template.queryForObject(sql, new Object[] { employeeId }, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Update all attributes except employeeId and userId
     */
    @Override
    public void update(Employee employee) {
        String sql = "UPDATE Employee SET basicSalary = ?, joinDate = ?, endDate = ?, panNumber = ?, accountNumber = ? WHERE employeeId = ?";
        template.update(sql, employee.getBasicSalary(), employee.getJoinDate(), employee.getEndDate(),
                employee.getPanNumber(), employee.getAccountNumber(), employee.getEmployeeId());
    }

    /**
     * Update all attributes except employeeId, userId, basic salary, join date, end
     * date
     */
    @Override
    public void updateOwnProfile(Employee employee) {
        String sql = "UPDATE Employee SET panNumber = ?, accountNumber = ? WHERE employeeId = ?";
        template.update(sql, employee.getPanNumber(), employee.getAccountNumber(), employee.getEmployeeId());
    }

    @Override
    public void delete(int employeeId) {
        String sql = "DELETE FROM Employee WHERE employeeId = ?";
        template.update(sql, employeeId);
    }

}
