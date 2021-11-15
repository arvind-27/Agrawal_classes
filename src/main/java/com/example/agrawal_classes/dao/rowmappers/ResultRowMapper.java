package com.example.agrawal_classes.dao.rowmappers;

import com.example.agrawal_classes.model.Result;
import com.example.agrawal_classes.model.Student;
import com.example.agrawal_classes.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultRowMapper implements RowMapper<Result> {

    @Override
    public Result mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(rs, rowNum);

        Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(rs, rowNum);
        student.setUser(user);

        Result result = (new BeanPropertyRowMapper<>(Result.class)).mapRow(rs, rowNum);
        result.setStudent(student);

        return result;
    }
}
