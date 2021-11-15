package com.example.agrawal_classes.dao.rowmappers;

import com.example.agrawal_classes.model.Course;
import com.example.agrawal_classes.model.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestRowMapper implements RowMapper<Test> {

    @Override
    public Test mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = (new BeanPropertyRowMapper<>(Course.class)).mapRow(rs, rowNum);

        Test test = (new BeanPropertyRowMapper<>(Test.class)).mapRow(rs, rowNum);
        test.setCourse(course);

        return test;
    }
}
