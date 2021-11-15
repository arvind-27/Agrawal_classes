package com.example.agrawal_classes.dao.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.agrawal_classes.model.Transaction;
import com.example.agrawal_classes.model.Enrollment;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

public class EnrollmentRowMapper implements RowMapper<Enrollment> {

    @Override
    public Enrollment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Transaction transaction = (new BeanPropertyRowMapper<>(Transaction.class)).mapRow(rs, rowNum);

        Enrollment enrollment = (new BeanPropertyRowMapper<>(Enrollment.class)).mapRow(rs, rowNum);
        enrollment.setTransaction(transaction);
        return enrollment;
    }
}
