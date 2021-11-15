package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Complaint;

import java.util.List;

public interface ComplaintDao {
    public void save(Complaint complaint);

    public Complaint get(int complaintId);

    public List<Complaint> getAll();

    public List<Complaint> getAllByStudentId(int studentId);

    public void resolve(int complaintId, String response);

    public void updateStudent(Complaint complaint);

    public void updateAdmin(Complaint complaint);

    public void delete(int complaintId);
}
