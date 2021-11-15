package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Feedback;
import com.example.agrawal_classes.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class FeedbackDaoImpl implements FeedbackDao {
    @Autowired
    private JdbcTemplate template;

    @Autowired
    private DateTimeUtil dateTimeUtil;

    @Override
    public void save(Feedback feedback) {
        String sql = "INSERT INTO Feedback (studentId, employeeId, date, time, subject, message, response) VALUES (?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, feedback.getStudentId(), feedback.getEmployeeId(),
                dateTimeUtil.getCurrentDateTime("yyyy-MM-dd"), dateTimeUtil.getCurrentDateTime("HH:mm:ss"),
                feedback.getSubject(), feedback.getMessage(), feedback.getResponse());
    }

    @Override
    public Feedback get(int feedbackId) {
        try {
            String sql = "SELECT * FROM Feedback WHERE feedbackId = ?";
            Feedback feedback = template.queryForObject(sql, new Object[] { feedbackId },
                    new BeanPropertyRowMapper<>(Feedback.class));
            return feedback;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Feedback> getAll() {
        String sql = "SELECT * FROM Feedback";
        List<Feedback> feedbacks = template.query(sql, new BeanPropertyRowMapper<>(Feedback.class));
        return feedbacks;
    }

    @Override
    public List<Feedback> getAllByStudentId(int studentId) {
        String sql = "SELECT * FROM Feedback WHERE studentId = ?";
        List<Feedback> feedbacks = template.query(sql, new Object[] { studentId },
                new BeanPropertyRowMapper<>(Feedback.class));
        return feedbacks;
    }

    @Override
    public List<Feedback> getAllByEmployeeId(int employeeId) {
        String sql = "SELECT * FROM Feedback WHERE employeeId = ?";
        List<Feedback> feedbacks = template.query(sql, new Object[] { employeeId },
                new BeanPropertyRowMapper<>(Feedback.class));
        return feedbacks;
    }

    @Override
    public void respond(int feedbackId, String response) {
        String sql = "UPDATE Feedback SET response = ? WHERE feedbackId = ?";
        template.update(sql, response, feedbackId);
    }

    /**
     * Update all attributes except date, time, studentId, employeeId
     */
    @Override
    public void update(Feedback feedback) {
        String sql = "UPDATE Feedback SET subject = ?, message = ?, response = ? WHERE feedbackId = ?";
        template.update(sql, feedback.getSubject(), feedback.getMessage(), feedback.getResponse(), feedback.getFeedbackId());
    }

    @Override
    public void delete(int feedbackId) {
        String sql = "DELETE FROM Feedback WHERE feedbackId = ?";
        template.update(sql, feedbackId);
    }

}
