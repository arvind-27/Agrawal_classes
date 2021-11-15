package com.example.agrawal_classes.dao.rowmappers;

import com.example.agrawal_classes.model.Attendance;
import com.example.agrawal_classes.model.Employee;
import com.example.agrawal_classes.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AttendanceRowMapper implements RowMapper<Attendance> {

    @Override
    public Attendance mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(rs, rowNum);

        Employee employee = (new BeanPropertyRowMapper<>(Employee.class)).mapRow(rs, rowNum);
        employee.setUser(user);

        Attendance attendance = (new BeanPropertyRowMapper<>(Attendance.class)).mapRow(rs, rowNum);
        attendance.setEmployee(employee);
        return attendance;
    }
}
