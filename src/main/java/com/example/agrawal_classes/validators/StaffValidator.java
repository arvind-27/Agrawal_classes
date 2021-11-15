package com.example.agrawal_classes.validators;

import com.example.agrawal_classes.model.User;
import com.example.agrawal_classes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class StaffValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("employee.user.username", "Duplicate.user.username");
        }
        if (userService.findByEmailAddress(user.getEmailAddress()) != null) {
            errors.rejectValue("employee.user.emailAddress", "Duplicate.user.emailAddress");
        }

    }
}
