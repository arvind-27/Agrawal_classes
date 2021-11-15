package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.dao.rowmappers.BatchRowMapper;
import com.example.agrawal_classes.model.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class BatchDaoImpl implements com.example.agrawal_classes.dao.BatchDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public void save(Batch batch) {
        String sql = "INSERT INTO Batch (batchId, courseId, batchName, fee, roomNumber, startTime, endTime) VALUES (?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, batch.getBatchId(), batch.getCourse().getCourseId(), batch.getBatchName(), batch.getFee(),
                batch.getRoomNumber(), batch.getStartTime(), batch.getEndTime());
    }

    @Override
    public List<Batch> getAll() {
        String sql = "SELECT * FROM Batch NATURAL JOIN Course";
        List<Batch> batches = template.query(sql, new BatchRowMapper());
        return batches;
    }

    @Override
    public List<Batch> getAllByCourseId(String courseId) {
        String sql = "SELECT * FROM Batch NATURAL JOIN Course WHERE courseId = ?";
        List<Batch> batches = template.query(sql, new Object[] {courseId}, new BatchRowMapper());
        return batches;
    }

    @Override
    public List<Batch> getAllByTeacherId(int teacherId) {
        String sql = "SELECT * FROM Batch NATURAL JOIN Course NATURAL JOIN TeacherBatchDetails WHERE teacherId = ?";
        List<Batch> batches = template.query(sql, new Object[] { teacherId }, new BatchRowMapper());
        return batches;
    }

    @Override
    public List<Batch> getAllBySubjectId(String subjectId) {
        String sql = "SELECT * FROM Batch NATURAL JOIN Course NATURAL JOIN CourseSubjectDetails WHERE subjectId = ?";
        List<Batch> batches = template.query(sql, new Object[] { subjectId }, new BatchRowMapper());
        return batches;
    }

    @Override
    public List<Batch> getAllByStaffId(int staffId) {
        String sql = "SELECT * FROM Batch NATURAL JOIN Course NATURAL JOIN StaffBatchDetails WHERE staffId = ?";
        List<Batch> batches = template.query(sql, new Object[] { staffId }, new BatchRowMapper());
        return batches;
    }

    @Override
    public Batch get(String batchId, String courseId) {
        try {
            String sql = "SELECT * FROM Batch NATURAL JOIN Course WHERE batchId = ? AND courseId = ?";
            Batch batch = template.queryForObject(sql, new Object[] { batchId, courseId }, new BatchRowMapper());
            return batch;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int getFee(String batchId, String courseId) {
        try {
            String sql = "SELECT fee FROM Batch NATURAL JOIN Course WHERE batchId = ? AND courseId = ?";
            return template.queryForObject(sql, new Object[] { batchId, courseId }, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    /**
     * Update all attributes except batchId and courseId
     */
    @Override
    public void update(Batch batch) {
        String sql = "UPDATE Batch SET batchName = ?, fee = ?, roomNumber = ?, startTime = ?, endTime = ? WHERE batchId = ? AND courseId = ?";
        template.update(sql, batch.getBatchName(), batch.getFee(), batch.getRoomNumber(), batch.getStartTime(), batch.getEndTime(),
                batch.getBatchId(), batch.getCourse().getCourseId());
    }

    @Override
    public void delete(String batchId, String courseId) {
        String sql = "DELETE FROM Batch WHERE batchId = ? AND courseId = ?";
        template.update(sql, batchId, courseId);
    }

}
