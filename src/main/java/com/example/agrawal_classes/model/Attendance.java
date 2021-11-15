package com.example.agrawal_classes.model;

import java.sql.Date;

public class Attendance {
    private Date date;
    private Employee employee;
    private boolean isPresent;
    private String remarks;

    public Attendance() {
        employee = new Employee();
    }

    public Attendance(Date date, Employee employee, boolean isPresent, String remarks) {
        this.date = date;
        this.employee = employee;
        this.isPresent = isPresent;
        this.remarks = remarks;
    }

    /**
     * @return Date return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return Employee return the employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * @param employee the employee to set
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * @return boolean return the isPresent
     */
    public boolean isIsPresent() {
        return isPresent;
    }

    /**
     * @param isPresent the isPresent to set
     */
    public void setIsPresent(boolean isPresent) {
        this.isPresent = isPresent;
    }

    /**
     * @return String return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "{" +
            " date='" + getDate() + "'" +
            ", employee='" + getEmployee() + "'" +
            ", isPresent='" + isIsPresent() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }

}
