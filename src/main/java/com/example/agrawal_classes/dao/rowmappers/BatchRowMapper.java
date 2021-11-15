package com.example.agrawal_classes.dao.rowmappers;

import com.example.agrawal_classes.model.Batch;
import com.example.agrawal_classes.model.Course;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BatchRowMapper implements RowMapper<Batch> {

    @Override
    public Batch mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = (new BeanPropertyRowMapper<>(Course.class)).mapRow(rs, rowNum);

        Batch batch = (new BeanPropertyRowMapper<>(Batch.class)).mapRow(rs, rowNum);
        batch.setCourse(course);

        return batch;
    }
}
