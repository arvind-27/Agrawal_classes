package com.example.agrawal_classes.dao.rowmappers;

import com.example.agrawal_classes.model.Employee;
import com.example.agrawal_classes.model.Subject;
import com.example.agrawal_classes.model.Teacher;
import com.example.agrawal_classes.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherRowMapper implements RowMapper<Teacher> {

    @Override
    public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(rs, rowNum);

        Employee employee = (new BeanPropertyRowMapper<>(Employee.class)).mapRow(rs, rowNum);
        employee.setUser(user);

        Subject subject = (new BeanPropertyRowMapper<>(Subject.class)).mapRow(rs, rowNum);

        Teacher teacher = (new BeanPropertyRowMapper<>(Teacher.class)).mapRow(rs, rowNum);
        teacher.setEmployee(employee);
        teacher.setSubject(subject);
        return teacher;

    }
}
