package com.example.agrawal_classes.validators;

import com.example.agrawal_classes.dao.AttendanceDao;
import com.example.agrawal_classes.model.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.sql.Date;

@Component
public class AttendanceValidator implements Validator {
    @Autowired
    private AttendanceDao attendanceDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return Attendance.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Attendance attendance = (Attendance) target;
        int employeeId = attendance.getEmployee().getEmployeeId();
        Date date = attendance.getDate();

        if (attendanceDao.get(date, employeeId) != null) {
            errors.rejectValue("employee.employeeId", "Duplicate.attendance.employeeId");
        }
    }
}
