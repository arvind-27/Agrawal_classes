package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Attendance;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface AttendanceDao {
    public void save(Attendance attendance);

    public Attendance get(Date date, int employeeId);

    public List<Map<String, Object>> getAllEmployeeWisePresent();

    public List<Map<String, Object>> getAllEmployeeWiseAbsent();

    public List<Attendance> getAllByDate(Date date);

    public List<Attendance> getAllByDateForTeacher(Date date);

    public List<Attendance> getAllByDateForStaff(Date date);

    public List<Attendance> getAllByEmployeeId(int employeeId);

    public void update(Attendance attendance);

    public void delete(Date date, int employeeID);
}
