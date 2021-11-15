package com.example.agrawal_classes.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

public class Payroll {
    @NotEmpty
    private String paymentRefNo;

    @Min(1)
    @Max(12)
    private int month;

    @Min(2018)
    private int year;

    private double salaryCredited;

    @NotNull
    private Date dateCredited;

    private Employee Employee;

    public Payroll() {
    }

    public Payroll(String paymentRefNo, int month, int year, double salaryCredited, Date dateCredited, Employee Employee) {
        this.paymentRefNo = paymentRefNo;
        this.month = month;
        this.year = year;
        this.salaryCredited = salaryCredited;
        this.dateCredited = dateCredited;
        this.Employee = Employee;
    }

    /**
     * @return String return the paymentRefNo
     */
    public String getPaymentRefNo() {
        return paymentRefNo;
    }

    /**
     * @param paymentRefNo the paymentRefNo to set
     */
    public void setPaymentRefNo(String paymentRefNo) {
        this.paymentRefNo = paymentRefNo;
    }

    /**
     * @return int return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * @return int return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return double return the salaryCredited
     */
    public double getSalaryCredited() {
        return salaryCredited;
    }

    /**
     * @param salaryCredited the salaryCredited to set
     */
    public void setSalaryCredited(double salaryCredited) {
        this.salaryCredited = salaryCredited;
    }

    /**
     * @return Date return the dateCredited
     */
    public Date getDateCredited() {
        return dateCredited;
    }

    /**
     * @param dateCredited the dateCredited to set
     */
    public void setDateCredited(Date dateCredited) {
        this.dateCredited = dateCredited;
    }

    /**
     * @return Employee return the Employee
     */
    public Employee getEmployee() {
        return Employee;
    }

    /**
     * @param Employee the Employee to set
     */
    public void setEmployee(Employee Employee) {
        this.Employee = Employee;
    }

    @Override
    public String toString() {
        return "{" +
            " paymentRefNo='" + getPaymentRefNo() + "'" +
            ", month='" + getMonth() + "'" +
            ", year='" + getYear() + "'" +
            ", salaryCredited='" + getSalaryCredited() + "'" +
            ", dateCredited='" + getDateCredited() + "'" +
            ", Employee='" + getEmployee() + "'" +
            "}";
    }

}
