package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Teacher;
import com.example.agrawal_classes.utils.PreparedStatementUtil;
import com.example.agrawal_classes.dao.rowmappers.TeacherRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Transactional
@Repository
public class TeacherDaoImpl implements TeacherDao {
    @Autowired
    private JdbcTemplate template;

    @Autowired
    private PreparedStatementUtil preparedStatementUtil;

    @Override
    public Teacher save(Teacher teacher) {
        String sql = "INSERT INTO Teacher (gender, dateOfBirth, houseNumber, street, city, state, bachelorsDegree, mastersDegree, "
                + "doctoralDegree, employeeId, subjectId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement preparedStatement = conn.prepareStatement(sql, new String[] {"teacherId"});
                preparedStatementUtil.setParameters(preparedStatement, teacher.getGender(), teacher.getDateOfBirth(),
                        teacher.getHouseNumber(), teacher.getStreet(), teacher.getCity(), teacher.getState(),
                        teacher.getBachelorsDegree(), teacher.getMastersDegree(),
                        teacher.getDoctoralDegree(), teacher.getEmployee().getEmployeeId(), teacher.getSubject().getSubjectId());
                return preparedStatement;
            }
        }, keyHolder);
        int teacherId = keyHolder.getKey().intValue();
        teacher.setTeacherId(teacherId);
        return teacher;
    }

    @Override
    public List<Teacher> getAll() {
        String sql = "SELECT * FROM Teacher NATURAL JOIN Employee NATURAL JOIN User NATURAL JOIN Subject";
        List<Teacher> teachers = template.query(sql, new TeacherRowMapper());
        return teachers;
    }

    @Override
    public List<Teacher> getAllByBatch(String batchId, String courseId) {
        String sql = "SELECT * FROM Teacher NATURAL JOIN TeacherBatchDetails NATURAL JOIN Employee NATURAL JOIN User WHERE batchId = ? AND courseId = ?";
        List<Teacher> teachers = template.query(sql, new Object[] { batchId, courseId }, new TeacherRowMapper());
        return teachers;
    }

    @Override
    public List<Teacher> getTeachersInBatch(String batchId, String courseId) {
        String sql = "SELECT * FROM Teacher NATURAL JOIN Employee NATURAL JOIN User NATURAL JOIN TeacherBatchDetails WHERE batchId = ? AND courseId = ?";
        List<Teacher> subjects = template.query(sql, new Object[] { batchId, courseId }, new TeacherRowMapper());
        return subjects;
    }

    @Override
    public List<Teacher> getTeachersNotInBatch(String batchId, String courseId) {
        String sql = "SELECT * FROM Teacher NATURAL JOIN Employee NATURAL JOIN User WHERE teacherId NOT IN (SELECT teacherId FROM TeacherBatchDetails WHERE batchId = ? AND courseId = ?)";
        List<Teacher> subjects = template.query(sql, new Object[] { batchId, courseId }, new TeacherRowMapper());
        return subjects;
    }

    @Override
    public Teacher getByEmployeeId(int employeeId) {
        try {
            String sql = "SELECT * FROM Teacher NATURAL JOIN Employee NATURAL JOIN User NATURAL JOIN Subject WHERE employeeId = ?";
            return (Teacher) template.queryForObject(sql, new Object[] { employeeId }, new TeacherRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Teacher getByUserId(int userId) {
        try {
            String sql = "SELECT * FROM Teacher NATURAL JOIN Employee NATURAL JOIN User WHERE userId = ?";
            return (Teacher) template.queryForObject(sql, new Object[] { userId }, new TeacherRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer getTeacherIdByUserId(int userId) {
        try {
            String sql = "SELECT teacherId FROM Teacher NATURAL JOIN Employee NATURAL JOIN User WHERE userId = ?";
            return template.queryForObject(sql, new Object[] { userId }, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Update all attributes except teacherId and employeeId
     */
    @Override
    public void update(Teacher teacher) {
        String sql = "UPDATE Teacher SET gender = ?, dateOfBirth = ?, houseNumber = ?, street = ?, city = ?, state = ?, "
                + "bachelorsDegree = ?, mastersDegree = ?, doctoralDegree = ?, subjectId = ? WHERE teacherId = ?";
        template.update(sql, teacher.getGender(), teacher.getDateOfBirth(), teacher.getHouseNumber(),
                teacher.getStreet(), teacher.getCity(), teacher.getState(),
                teacher.getBachelorsDegree(), teacher.getMastersDegree(), teacher.getDoctoralDegree(),
                teacher.getSubject().getSubjectId(), teacher.getTeacherId());
    }

    @Override
    public void delete(int teacherId) {
        String sql = "DELETE FROM Teacher WHERE teacherId = ?";
        template.update(sql, teacherId);
    }

}
