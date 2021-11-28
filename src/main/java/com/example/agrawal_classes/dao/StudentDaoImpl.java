package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.dao.rowmappers.StudentRowMapper;
import com.example.agrawal_classes.model.Student;
import com.example.agrawal_classes.utils.PreparedStatementUtil;
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
public class StudentDaoImpl implements StudentDao {
    @Autowired
    private JdbcTemplate template;

    @Autowired
    private PreparedStatementUtil preparedStatementUtil;

    @Override
    public Student save(Student student) {
        String sql = "INSERT INTO Student (gender, dateOfBirth, houseNumber, street, city, state, schoolAttending, "
                + "userId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator(){
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement preparedStatement = conn.prepareStatement(sql, new String[] {"studentId"});
                preparedStatementUtil.setParameters(preparedStatement, student.getGender(), student.getDateOfBirth(),
                        student.getHouseNumber(), student.getStreet(), student.getCity(), student.getState(),
                        student.getSchoolAttending(), student.getUser().getUserId());
                return preparedStatement;
            }
        }, keyHolder);
        int studentId = keyHolder.getKey().intValue();
        student.setStudentId(studentId);
        return student;
    }

    @Override
    public List<Student> getAll() {
        String sql = "SELECT * FROM Student NATURAL JOIN User";
        List<Student> students = template.query(sql, new StudentRowMapper());
        return students;
    }

    @Override
    public List<Student> getAllByCourseId(String courseId) {
        String sql = "SELECT * FROM Student NATURAL JOIN User NATURAL JOIN Enrollment WHERE courseId = ?";
        List<Student> students = template.query(sql, new Object[] { courseId },  new StudentRowMapper());
        return students;
    }

    @Override
    public Student get(int studentId) {
        try {
            String sql = "SELECT * FROM Student NATURAL JOIN User NATURAL JOIN Guardian WHERE studentId = ?";
            return (Student) template.queryForObject(sql, new Object[] {
                    studentId },
                    new StudentRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Student getByUserId(int userId) {
        try {
            String sql = "SELECT * FROM Student NATURAL JOIN User WHERE userId = ?";
            return (Student) template.queryForObject(sql, new Object[] { userId }, new StudentRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer getStudentIdByUserId(int userId) {
        try {
            String sql = "SELECT studentId FROM Student WHERE userId = ?";
            return template.queryForObject(sql, new Object[] { userId }, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Update all attributes except studentId and userId
     */
    @Override
    public void update(Student student) {
        String sql = "UPDATE Student SET gender = ?, dateOfBirth = ?, houseNumber = ?, street = ?, city = ?, state = ?, "
                + "schoolAttending = ? WHERE studentId = ?";
        template.update(sql, student.getGender(), student.getDateOfBirth(), student.getHouseNumber(),
                student.getStreet(), student.getCity(), student.getState(),
                student.getSchoolAttending(), student.getStudentId());
    }

    @Override
    public void delete(int studentId) {
        String sql = "DELETE FROM Student WHERE studentId = ?";
        template.update(sql, studentId);
    }

}
