package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Complaint;
import com.example.agrawal_classes.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class ComplaintDaoImpl implements com.example.agrawal_classes.dao.ComplaintDao {
    @Autowired
    private JdbcTemplate template;

    @Autowired
    private DateTimeUtil dateTimeUtil;

    @Override
    public void save(Complaint complaint) {
        String sql = "INSERT INTO Complaint (date, time, subject, description, response, isResolved, studentId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, dateTimeUtil.getCurrentDateTime("yyyy-MM-dd"), dateTimeUtil.getCurrentDateTime("HH:mm:ss"),
                complaint.getSubject(), complaint.getDescription(), complaint.getResponse(), complaint.isIsResolved(),
                complaint.getStudentId());
    }

    @Override
    public Complaint get(int complaintId) {
        try {
            String sql = "SELECT * FROM Complaint WHERE complaintId = ?";
            Complaint complaint = template.queryForObject(sql, new Object[] { complaintId },
                    new BeanPropertyRowMapper<>(Complaint.class));
            return complaint;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Complaint> getAll() {
        String sql = "SELECT * FROM Complaint";
        List<Complaint> complaints = template.query(sql, new BeanPropertyRowMapper<>(Complaint.class));
        return complaints;
    }

    @Override
    public List<Complaint> getAllByStudentId(int studentId) {
        String sql = "SELECT * FROM Complaint WHERE studentId = ?";
        List<Complaint> complaints = template.query(sql, new Object[] { studentId }, new BeanPropertyRowMapper<>(Complaint.class));
        return complaints;
    }

    @Override
    public void resolve(int complaintId, String response) {
        String sql = "UPDATE Complaint SET response = ?, isResolved = ? WHERE complaintId = ?";
        template.update(sql, response, true, complaintId);
    }

    /**
     * Update the subject and description of complaint
     */
    @Override
    public void updateStudent(Complaint complaint) {
        String sql = "UPDATE Complaint SET subject = ?, description = ? WHERE complaintId = ?";
        template.update(sql, complaint.getSubject(), complaint.getDescription(), complaint.getComplaintId());
    }

    /**
     * Response and isResolved of complaint
     */
    @Override
    public void updateAdmin(Complaint complaint) {
        String sql = "UPDATE Complaint SET response = ?, isResolved = ? WHERE complaintId = ?";
        template.update(sql, complaint.getResponse(), complaint.isIsResolved(), complaint.getComplaintId());
    }

    @Override
    public void delete(int complaintId) {
        String sql = "DELETE FROM Complaint WHERE complaintId = ?";
        template.update(sql, complaintId);
    }

}
