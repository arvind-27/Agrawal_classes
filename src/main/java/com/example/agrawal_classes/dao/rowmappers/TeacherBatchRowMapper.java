package com.example.agrawal_classes.dao.rowmappers;

import com.example.agrawal_classes.model.Batch;
import com.example.agrawal_classes.model.TeacherBatchDetails;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherBatchRowMapper implements RowMapper<TeacherBatchDetails> {

    @Override
    public TeacherBatchDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        Batch batch = (new BeanPropertyRowMapper<>(Batch.class)).mapRow(rs, rowNum);

        TeacherBatchDetails teacherBatch = (new BeanPropertyRowMapper<>(TeacherBatchDetails.class)).mapRow(rs, rowNum);
        teacherBatch.setBatch(batch);
        return teacherBatch;
    }
}
