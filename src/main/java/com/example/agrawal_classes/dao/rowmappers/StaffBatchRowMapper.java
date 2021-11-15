package com.example.agrawal_classes.dao.rowmappers;

import com.example.agrawal_classes.model.Batch;
import com.example.agrawal_classes.model.StaffBatchDetails;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffBatchRowMapper implements RowMapper<StaffBatchDetails> {

    @Override
    public StaffBatchDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        Batch batch = (new BeanPropertyRowMapper<>(Batch.class)).mapRow(rs, rowNum);

        StaffBatchDetails staffBatch = (new BeanPropertyRowMapper<>(StaffBatchDetails.class)).mapRow(rs, rowNum);
        staffBatch.setBatch(batch);
        return staffBatch;
    }
}
