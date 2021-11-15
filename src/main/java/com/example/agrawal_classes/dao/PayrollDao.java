package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Payroll;

import java.util.List;

public interface PayrollDao {
    public void save(Payroll payroll);

    public Payroll get(String paymentRefNo);

    public List<Payroll> getAll();

    public List<Payroll> getAllByEmployeeId(int employeeId);

    public List<Payroll> getAllByMonthYear(int month, int year);

    public void update(Payroll payroll);

    public void delete(String paymentRefNo);
}
