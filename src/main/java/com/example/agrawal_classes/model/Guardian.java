package com.example.agrawal_classes.model;

import javax.validation.constraints.NotEmpty;

public class Guardian {
    @NotEmpty
    private String name;

    private int studentId;

    private String occupation;

    @NotEmpty
    private String address;

    @NotEmpty
    private String email;

    @NotEmpty
    private String relationWithStudent;

    public Guardian() {
    }

    public Guardian(String name, int studentId, String occupation, String address, String email, String relationWithStudent) {
        this.name = name;
        this.studentId = studentId;
        this.occupation = occupation;
        this.address = address;
        this.email = email;
        this.relationWithStudent = relationWithStudent;

    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return int return the studentId
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * @return String return the occupation
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * @param occupation the occupation to set
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * @return String return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return String return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String return the relationWithStudent
     */
    public String getRelationWithStudent() {
        return relationWithStudent;
    }

    /**
     * @param relationWithStudent the relationWithStudent to set
     */
    public void setRelationWithStudent(String relationWithStudent) {
        this.relationWithStudent = relationWithStudent;
    }


    @Override
    public String toString() {
        return "Guardian{" +
                "name='" + name + '\'' +
                ", studentId=" + studentId +
                ", occupation='" + occupation + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", relationWithStudent='" + relationWithStudent + '\'' +
                '}';
    }
}
