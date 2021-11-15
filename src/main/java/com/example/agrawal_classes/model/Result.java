package com.example.agrawal_classes.model;

import javax.validation.constraints.PositiveOrZero;

public class Result {
    private Student student;
    private int testId;

    @PositiveOrZero
    private int marksScored;

    private boolean hasAppliedRecheck;
    private boolean isDoneRecheck;
    private String recheckComments;

    public Result() {
        student = new Student();
    }

    public Result(Student student, int testId, int marksScored, boolean hasAppliedRecheck, boolean isDoneRecheck, String recheckComments) {
        this.student = student;
        this.testId = testId;
        this.marksScored = marksScored;
        this.hasAppliedRecheck = hasAppliedRecheck;
        this.isDoneRecheck = isDoneRecheck;
        this.recheckComments = recheckComments;
    }

    /**
     * @return Student return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @param student the student to set
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * @return int return the testId
     */
    public int getTestId() {
        return testId;
    }

    /**
     * @param testId the testId to set
     */
    public void setTestId(int testId) {
        this.testId = testId;
    }

    /**
     * @return int return the marksScored
     */
    public int getMarksScored() {
        return marksScored;
    }

    /**
     * @param marksScored the marksScored to set
     */
    public void setMarksScored(int marksScored) {
        this.marksScored = marksScored;
    }

    /**
     * @return boolean return the hasAppliedRecheck
     */
    public boolean isHasAppliedRecheck() {
        return hasAppliedRecheck;
    }

    /**
     * @param hasAppliedRecheck the hasAppliedRecheck to set
     */
    public void setHasAppliedRecheck(boolean hasAppliedRecheck) {
        this.hasAppliedRecheck = hasAppliedRecheck;
    }

    /**
     * @return boolean return the isDoneRecheck
     */
    public boolean isIsDoneRecheck() {
        return isDoneRecheck;
    }

    /**
     * @param isDoneRecheck the isDoneRecheck to set
     */
    public void setIsDoneRecheck(boolean isDoneRecheck) {
        this.isDoneRecheck = isDoneRecheck;
    }

    /**
     * @return String return the recheckComments
     */
    public String getRecheckComments() {
        return recheckComments;
    }

    /**
     * @param recheckComments the recheckComments to set
     */
    public void setRecheckComments(String recheckComments) {
        this.recheckComments = recheckComments;
    }

    @Override
    public String toString() {
        return "{" +
            " student='" + getStudent() + "'" +
            ", testId='" + getTestId() + "'" +
            ", marksScored='" + getMarksScored() + "'" +
            ", hasAppliedRecheck='" + isHasAppliedRecheck() + "'" +
            ", isDoneRecheck='" + isIsDoneRecheck() + "'" +
            ", recheckComments='" + getRecheckComments() + "'" +
            "}";
    }

}
