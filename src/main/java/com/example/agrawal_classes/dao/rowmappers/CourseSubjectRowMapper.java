package com.example.agrawal_classes.dao.rowmappers;

import com.example.agrawal_classes.model.CourseSubjectDetails;
import com.example.agrawal_classes.model.Subject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseSubjectRowMapper implements RowMapper<CourseSubjectDetails> {

    @Override
    public CourseSubjectDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        Subject subject = (new BeanPropertyRowMapper<>(Subject.class)).mapRow(rs, rowNum);

        CourseSubjectDetails courseSubject = (new BeanPropertyRowMapper<>(CourseSubjectDetails.class)).mapRow(rs, rowNum);
        courseSubject.setSubject(subject);
        return courseSubject;
    }
}
