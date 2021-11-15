package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.Guardian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class GuardianDaoImpl implements com.example.agrawal_classes.dao.GuardianDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public void save(Guardian guardian) {
        String sql = "INSERT INTO Guardian (name, studentId, occupation, address, email, relationWithStudent) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        template.update(sql, guardian.getName(), guardian.getStudentId(), guardian.getOccupation(),
                guardian.getAddress(), guardian.getEmail(), guardian.getRelationWithStudent());
    }

    @Override
    public String getNameByStudentId(int studentId) {
        try {
            String sql = "SELECT name FROM Guardian WHERE studentId = ?";
            String guardianName = template.queryForObject(sql, new Object[] { studentId }, String.class);
            return guardianName;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Update all attributes except studentId
     */
    @Override
    public void update(Guardian guardian) {
        String sql = "UPDATE Guardian SET name = ?, occupation = ?, address = ?, email = ?, relationWithStudent = ? WHERE studentId = ?";
        template.update(sql, guardian.getName(), guardian.getOccupation(), guardian.getAddress(),
                guardian.getEmail(), guardian.getRelationWithStudent(), guardian.getStudentId());
    }

}
