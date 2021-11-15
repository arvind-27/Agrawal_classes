package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.dao.rowmappers.TestRowMapper;
import com.example.agrawal_classes.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class TestDaoImpl implements com.example.agrawal_classes.dao.TestDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public void save(Test test) {
        String sql = "INSERT INTO Test (testName, roomNumber, testDate, startTime, endTime, maximumMarks, difficulty, courseId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, test.getTestName(), test.getRoomNumber(), test.getTestDate(), test.getStartTime(),
                test.getEndTime(), test.getMaximumMarks(), test.getDifficulty(), test.getCourse().getCourseId());
    }

    @Override
    public List<Test> getAll() {
        String sql = "SELECT * FROM Test NATURAL JOIN Course ORDER BY testDate DESC, startTime DESC";
        List<Test> tests = template.query(sql, new TestRowMapper());
        return tests;
    }

    @Override
    public List<Test> getAllByCourseId(String courseId) {
        String sql = "SELECT * FROM Test NATURAL JOIN Course WHERE courseId = ? ORDER BY testDate DESC, startTime DESC";
        List<Test> tests = template.query(sql, new Object[] {courseId}, new TestRowMapper());
        return tests;
    }

    @Override
    public List<Map<String, Object>> getAllByStudentId(int studentId) {
        String sql = "SELECT * FROM Test NATURAL JOIN Course NATURAL JOIN Enrollment LEFT JOIN Result USING (testId, studentId) WHERE studentId = ?";
        sql += " ORDER BY testDate DESC, startTime DESC";
        List<Map<String, Object>> tests = template.queryForList(sql, new Object[] { studentId });
        return tests;
    }

    @Override
    public Test get(int testId) {
        try {
            String sql = "SELECT * FROM Test NATURAL JOIN Course WHERE testId = ?";
            Test test = template.queryForObject(sql, new Object[] { testId }, new TestRowMapper());
            return test;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer getMaximumMarks(int testId) {
        try {
            String sql = "SELECT maximumMarks FROM Test WHERE testId = ?";
            Integer maximumMarks = template.queryForObject(sql, new Object[] { testId }, Integer.class);
            return maximumMarks;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Update all attributes except testId and courseId
     */
    @Override
    public void update(Test test) {
        String sql = "UPDATE Test SET testName = ?, roomNumber = ?, testDate = ?, startTime = ?, endTime = ?, maximumMarks = ?, "
                + "difficulty = ? WHERE testId = ?";
        template.update(sql, test.getTestName(), test.getRoomNumber(), test.getTestDate(), test.getStartTime(), test.getEndTime(),
                test.getMaximumMarks(), test.getDifficulty(), test.getTestId());
    }

    @Override
    public void delete(int testId) {
        String sql = "DELETE FROM Test WHERE testId = ?";
        template.update(sql, testId);
    }

}
