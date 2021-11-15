package com.example.agrawal_classes.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;

public class Employee {
    private int employeeId;
    private int basicSalary;
    private Date joinDate;
    private Date endDate;

    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "must be a valid PAN Number")
    private String panNumber;

    @Size(min = 5, max = 20)
    private String accountNumber;

    private User user;

    public Employee() {
        user = new User();
    }

    public Employee(int employeeId, int basicSalary, Date joinDate, Date endDate, String panNumber, String accountNumber, User user) {
        this.employeeId = employeeId;
        this.basicSalary = basicSalary;
        this.joinDate = joinDate;
        this.endDate = endDate;
        this.panNumber = panNumber;
        this.accountNumber = accountNumber;
        this.user = user;
    }


    /**
     * @return int return the employeeId
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return int return the basicSalary
     */
    public int getBasicSalary() {
        return basicSalary;
    }

    /**
     * @param basicSalary the basicSalary to set
     */
    public void setBasicSalary(int basicSalary) {
        this.basicSalary = basicSalary;
    }

    /**
     * @return Date return the joinDate
     */
    public Date getJoinDate() {
        return joinDate;
    }

    /**
     * @param joinDate the joinDate to set
     */
    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    /**
     * @return Date return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return String return the panNumber
     */
    public String getPanNumber() {
        return panNumber;
    }

    /**
     * @param panNumber the panNumber to set
     */
    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    /**
     * @return String return the accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * @param accountNumber the accountNumber to set
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * @return User return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param userId the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", basicSalary=" + basicSalary +
                ", joinDate=" + joinDate +
                ", endDate=" + endDate +
                ", panNumber='" + panNumber + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", user=" + user +
                '}';
    }
}
