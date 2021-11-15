package com.example.agrawal_classes.dao.rowmappers;

import com.example.agrawal_classes.model.Guardian;
import com.example.agrawal_classes.model.Student;
import com.example.agrawal_classes.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(rs, rowNum);

        Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(rs, rowNum);
        student.setUser(user);

        Guardian guardian = (new BeanPropertyRowMapper<>(Guardian.class)).mapRow(rs, rowNum);
        student.setGuardian(guardian);

        return student;
    }
}
