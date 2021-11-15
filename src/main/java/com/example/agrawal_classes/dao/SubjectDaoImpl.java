package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class SubjectDaoImpl implements com.example.agrawal_classes.dao.SubjectDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public void save(Subject subject) {
        String sql = "INSERT INTO Subject (subjectId, subjectName, description) VALUES (?, ?, ?)";
        template.update(sql, subject.getSubjectId(), subject.getSubjectName(), subject.getDescription());
    }

    @Override
    public List<Subject> getAll() {
        String sql = "SELECT * FROM Subject";
        List<Subject> subjects = template.query(sql, new BeanPropertyRowMapper<>(Subject.class));
        return subjects;
    }

    @Override
    public Subject get(String subjectId) {
        try {
            String sql = "SELECT * FROM Subject WHERE subjectId = ?";
            Subject subject = template.queryForObject(sql, new Object[] { subjectId },
                    new BeanPropertyRowMapper<>(Subject.class));
            return subject;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Subject getBySubjectName(String subjectName) {
        try {
            String sql = "SELECT * FROM Subject WHERE subjectName = ?";
            Subject subject = template.queryForObject(sql, new Object[] { subjectName },
                    new BeanPropertyRowMapper<>(Subject.class));
            return subject;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Subject> getSubjectsInCourse(String courseId) {
        String sql = "SELECT * FROM Subject NATURAL JOIN CourseSubjectDetails WHERE courseId = ?";
        List<Subject> subjects = template.query(sql, new Object[] { courseId },
                new BeanPropertyRowMapper<>(Subject.class));
        return subjects;
    }

    @Override
    public List<Subject> getSubjectsNotInCourse(String courseId) {
        String sql = "SELECT * FROM Subject WHERE subjectId NOT IN (SELECT subjectId FROM CourseSubjectDetails WHERE courseId = ?)";
        List<Subject> subjects = template.query(sql, new Object[] { courseId },
                new BeanPropertyRowMapper<>(Subject.class));
        return subjects;
    }

    @Override
    public List<String> getSubjectCodeByStudentId(int studentId) {
        String sql = "SELECT DISTINCT(S.subjectId) FROM Subject S, CourseSubjectDetails CS, Enrollment E WHERE E.studentId = ? AND E.courseId = CS.courseId AND CS.subjectId = S.subjectId";
        List<String> subjects = template.queryForList(sql, new Object[] { studentId }, String.class);
        return subjects;
    }

    @Override
    public String getSubjectCodeByTeacherId(int teacherId) {
        String sql = "SELECT subjectId FROM Teacher WHERE teacherId = ?";
        String subject = template.queryForObject(sql, new Object[] { teacherId }, String.class);
        return subject;
    }

    /**
     * Update all attributes except subjectId
     */
    @Override
    public void update(Subject subject) {
        String sql = "UPDATE Subject SET subjectName = ?, description = ? WHERE subjectId = ?";
        template.update(sql, subject.getSubjectName(), subject.getDescription(), subject.getSubjectId());
    }

    @Override
    public void delete(String subjectId) {
        String sql = "DELETE FROM Subject WHERE subjectId = ?";
        template.update(sql, subjectId);
    }

}
