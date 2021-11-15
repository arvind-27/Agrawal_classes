package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Staff;

import java.util.List;

public interface StaffDao {
    public Staff save(Staff staff);

    public List<Staff> getAll();

    public List<Staff> getAllByBatch(String batchId, String courseId);

    public List<Staff> getStaffsInBatch(String batchId, String courseId);

    public List<Staff> getStaffsNotInBatch(String batchId, String courseId);

    public Staff getByEmployeeId(int employeeId);

    public Staff getByUserId(int userId);

    public Integer getStaffIdByUserId(int userId);

    public void update(Staff staff);

    public void delete(int staffId);
}
