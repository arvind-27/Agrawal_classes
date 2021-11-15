package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.dao.rowmappers.AttendanceRowMapper;
import com.example.agrawal_classes.model.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class AttendanceDaoImpl implements com.example.agrawal_classes.dao.AttendanceDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public void save(Attendance attendance) {
        String sql = "INSERT INTO Attendance (date, employeeId, isPresent, remarks) VALUES (?, ?, ?, ?)";
        template.update(sql, attendance.getDate(), attendance.getEmployee().getEmployeeId(), attendance.isIsPresent(), attendance.getRemarks());
    }

    @Override
    public Attendance get(Date date, int employeeId) {
        try {
            String sql = "SELECT * FROM Attendance NATURAL JOIN Employee NATURAL JOIN User WHERE date = ? AND employeeId = ?";
            Attendance attendance = template.queryForObject(sql, new Object[] { date, employeeId },
                    new AttendanceRowMapper());
            return attendance;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getAllEmployeeWisePresent() {
        String sql = "SELECT employeeId, COUNT(date) AS count FROM Attendance WHERE isPresent = true GROUP BY employeeId ";
        sql += "UNION SELECT E.employeeId AS employeeId, 0 AS count FROM Employee E WHERE NOT EXISTS (SELECT A.employeeId FROM Attendance A WHERE isPresent = true AND A.employeeId = E.employeeId) ";
        sql += "ORDER BY employeeId";
        List<Map<String, Object>> presentCount = template.queryForList(sql);
        return presentCount;
    }

    @Override
    public List<Map<String, Object>> getAllEmployeeWiseAbsent() {
        String sql = "SELECT employeeId, COUNT(date) AS count FROM Attendance WHERE isPresent = false GROUP BY employeeId ";
        sql += "UNION SELECT E.employeeId AS employeeId, 0 AS count FROM Employee E WHERE NOT EXISTS (SELECT A.employeeId FROM Attendance A WHERE isPresent = false AND A.employeeId = E.employeeId) ";
        sql += "ORDER BY employeeId";
        List<Map<String, Object>> absentCount = template.queryForList(sql);
        return absentCount;
    }

    @Override
    public List<Attendance> getAllByDate(Date date) {
        String sql = "SELECT * FROM Attendance NATURAL JOIN Employee NATURAL JOIN User WHERE date = ?";
        List<Attendance> attendances = template.query(sql, new Object[] { date },
                new AttendanceRowMapper());
        return attendances;
    }

    @Override
    public List<Attendance> getAllByDateForTeacher(Date date) {
        String sql = "SELECT * FROM Attendance NATURAL JOIN Employee NATURAL JOIN User WHERE date = ? AND role='ROLE_TEACHER'";
        List<Attendance> attendances = template.query(sql, new Object[] { date }, new AttendanceRowMapper());
        return attendances;
    }

    @Override
    public List<Attendance> getAllByDateForStaff(Date date) {
        String sql = "SELECT * FROM Attendance NATURAL JOIN Employee NATURAL JOIN User WHERE date = ? AND role='ROLE_STAFF'";
        List<Attendance> attendances = template.query(sql, new Object[] { date }, new AttendanceRowMapper());
        return attendances;
    }

    @Override
    public List<Attendance> getAllByEmployeeId(int employeeId) {
        String sql = "SELECT * FROM Attendance NATURAL JOIN Employee NATURAL JOIN User WHERE employeeId = ?";
        List<Attendance> attendances = template.query(sql, new Object[] { employeeId }, new AttendanceRowMapper());
        return attendances;
    }

    /**
     * Update all attributes except date and employeeId
     */
    @Override
    public void update(Attendance attendance) {
        String sql = "UPDATE Attendance SET isPresent = ?, remarks = ? WHERE date = ? AND employeeId = ?";
        template.update(sql, attendance.isIsPresent(), attendance.getRemarks(), attendance.getDate(), attendance.getEmployee().getEmployeeId());
    }

    @Override
    public void delete(Date date, int employeeId) {
        String sql = "DELETE FROM Attendance WHERE date = ? AND employeeId = ?";
        template.update(sql, date, employeeId);
    }

}
