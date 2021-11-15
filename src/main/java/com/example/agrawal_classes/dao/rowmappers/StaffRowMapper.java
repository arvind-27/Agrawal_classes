package com.example.agrawal_classes.dao.rowmappers;

import com.example.agrawal_classes.model.Employee;
import com.example.agrawal_classes.model.Staff;
import com.example.agrawal_classes.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffRowMapper implements RowMapper<Staff> {

    @Override
    public Staff mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(rs, rowNum);

        Employee employee = (new BeanPropertyRowMapper<>(Employee.class)).mapRow(rs, rowNum);
        employee.setUser(user);

        Staff staff = (new BeanPropertyRowMapper<>(Staff.class)).mapRow(rs, rowNum);
        staff.setEmployee(employee);
        return staff;
    }
}
