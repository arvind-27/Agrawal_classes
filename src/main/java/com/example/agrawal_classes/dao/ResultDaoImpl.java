package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.dao.rowmappers.ResultRowMapper;
import com.example.agrawal_classes.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class ResultDaoImpl implements ResultDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public void save(Result result) {
        String sql = "INSERT INTO Result (studentId, testId, marksScored, hasAppliedRecheck, isDoneRecheck, recheckComments) VALUES (?, ?, ?, ?, ?, ?)";
        template.update(sql, result.getStudent().getStudentId(), result.getTestId(), result.getMarksScored(),
                result.isHasAppliedRecheck(), result.isIsDoneRecheck(), result.getRecheckComments());
    }

    @Override
    public List<Result> getAllByTestId(int testId) {
        String sql = "SELECT * FROM Result NATURAL JOIN Student NATURAL JOIN User WHERE testId = ? ORDER BY marksScored DESC";
        List<Result> results = template.query(sql, new Object[] { testId }, new ResultRowMapper());
        return results;
    }

    @Override
    public List<Result> getAllRechecksByTestId(int testId) {
        String sql = "SELECT * FROM Result NATURAL JOIN Student NATURAL JOIN User WHERE testId = ? AND hasAppliedRecheck = 1 ORDER BY marksScored DESC";
        List<Result> results = template.query(sql, new Object[] { testId }, new ResultRowMapper());
        return results;
    }

    @Override
    public Result get(int testId, int studentId) {
        try {
            String sql = "SELECT * FROM Result NATURAL JOIN Student NATURAL JOIN User WHERE testId = ? AND studentId = ?";
            Result result = template.queryForObject(sql, new Object[] { testId, studentId }, new ResultRowMapper());
            return result;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int isStudentAppearedInTest(int testId, int studentId) {
        String sql = "SELECT COUNT(*) FROM Result WHERE testId = ? AND studentId = ?";
        return template.queryForObject(sql, new Object[] { testId, studentId }, Integer.class);
    }

    @Override
    public void applyForRecheck(int testId, int studentId, String recheckComments) {
        String sql = "UPDATE Result SET hasAppliedRecheck = true, recheckComments = ? WHERE testId = ? AND studentId = ?";
        template.update(sql, recheckComments, testId, studentId);
    }

    @Override
    public void updateMarksAndMarkDone(int testId, int studentId, int marks) {
        String sql = "UPDATE Result SET isDoneRecheck = true, marksScored = ? WHERE testId = ? AND studentId = ?";
        template.update(sql, marks, testId, studentId);
    }

    /**
     * Update only the marks (to be done by staff)
     */
    @Override
    public void updateMarks(Result result) {
        String sql = "UPDATE Result SET marksScored = ? WHERE testId = ? AND studentId = ?";
        template.update(sql, result.getMarksScored(), result.getTestId(), result.getStudent().getStudentId());
    }

    /**
     * Update all attributes except testId and studentId
     */
    @Override
    public void update(Result result) {
        String sql = "UPDATE Result SET marksScored = ?, hasAppliedRecheck = ?, isDoneRecheck = ?, recheckComments = ? WHERE testId = ? AND studentId = ?";
        template.update(sql, result.getMarksScored(), result.isHasAppliedRecheck(), result.isIsDoneRecheck(),
                result.getRecheckComments(), result.getTestId(), result.getStudent().getStudentId());
    }

    @Override
    public void delete(int testId, int studentId) {
        String sql = "DELETE FROM Result WHERE testId = ? AND studentId = ?";
        template.update(sql, testId, studentId);
    }

}
