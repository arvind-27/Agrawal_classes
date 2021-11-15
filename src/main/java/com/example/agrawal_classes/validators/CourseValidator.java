package com.example.agrawal_classes.validators;

import com.example.agrawal_classes.dao.CourseDao;
import com.example.agrawal_classes.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CourseValidator implements Validator {
    @Autowired
    private CourseDao courseDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return Course.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Course course = (Course) target;
        String courseId = course.getCourseId();

        if (courseDao.get(courseId) != null) {
            errors.rejectValue("courseId", "Duplicate.course.courseId");
        }
    }
}
