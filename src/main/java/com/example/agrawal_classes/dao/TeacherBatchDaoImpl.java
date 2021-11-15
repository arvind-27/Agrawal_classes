package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.dao.rowmappers.TeacherBatchRowMapper;
import com.example.agrawal_classes.model.TeacherBatchDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class TeacherBatchDaoImpl implements com.example.agrawal_classes.dao.TeacherBatchDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public void save(String teacherId, String batchId, String courseId) {
        String sql = "INSERT INTO TeacherBatchDetails (teacherId, batchId, courseId) VALUES (?, ?, ?)";
        template.update(sql, teacherId, batchId, courseId);
    }

    @Override
    public List<TeacherBatchDetails> getAll() {
        String sql = "SELECT * FROM TeacherBatchDetails NATURAL JOIN Batch";
        return template.query(sql, new TeacherBatchRowMapper());
    }

    @Override
    public void delete(String teacherId, String batchId, String courseId) {
        String sql = "DELETE FROM TeacherBatchDetails WHERE teacherId = ? AND batchId = ? AND courseId = ?";
        template.update(sql, teacherId, batchId, courseId);
    }

}
