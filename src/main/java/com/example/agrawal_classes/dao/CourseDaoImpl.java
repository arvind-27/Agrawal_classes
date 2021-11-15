package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class CourseDaoImpl implements CourseDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public void save(Course course) {
        String sql = "INSERT INTO Course (courseId, courseName, description) VALUES (?, ?, ?)";
        template.update(sql, course.getCourseId(), course.getCourseName(), course.getDescription());
    }

    @Override
    public List<Course> getAll() {
        String sql = "SELECT * FROM Course";
        List<Course> courses = template.query(sql, new BeanPropertyRowMapper<>(Course.class));
        return courses;
    }

    @Override
    public List<Map<String, Object>> getAllList() {
        String sql = "SELECT * FROM Course";
        List<Map<String, Object>> courses = template.queryForList(sql);
        return courses;
    }

    @Override
    public Course get(String courseId) {
        try {
            String sql = "SELECT * FROM Course WHERE courseId = ?";
            Course course = template.queryForObject(sql, new Object[] { courseId },
                    new BeanPropertyRowMapper<>(Course.class));
            return course;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<String> getCourseIdByStudentId(int studentId) {
        String sql = "SELECT DISTINCT(courseId) FROM Enrollment WHERE studentId = ?";
        return template.queryForList(sql, new Object[] { studentId }, String.class);
    }

    /**
     * Update all attributes except courseId
     */
    @Override
    public void update(Course course) {
        String sql = "UPDATE Course SET courseName = ?, description = ? WHERE courseId = ?";
        template.update(sql, course.getCourseName(), course.getDescription(), course.getCourseId());
    }

    @Override
    public void delete(String courseId) {
        String sql = "DELETE FROM Course WHERE courseId = ?";
        template.update(sql, courseId);
    }

}
