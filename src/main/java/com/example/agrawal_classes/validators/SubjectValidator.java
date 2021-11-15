package com.example.agrawal_classes.validators;

import com.example.agrawal_classes.dao.SubjectDao;
import com.example.agrawal_classes.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SubjectValidator implements Validator {
    @Autowired
    private SubjectDao subjectDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return Subject.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Subject subject = (Subject) target;
        String subjectId = subject.getSubjectId();

        if (subjectDao.get(subjectId) != null) {
            errors.rejectValue("subjectId", "Duplicate.subject.subjectId");
        }
    }
}
