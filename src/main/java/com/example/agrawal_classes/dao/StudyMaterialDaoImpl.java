package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.StudyMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class StudyMaterialDaoImpl implements StudyMaterialDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public void save(StudyMaterial material) {
        String sql = "INSERT INTO StudyMaterial (subjectId, materialId, topicName, difficulty, description, filename) VALUES (?, ?, ?, ?, ?, ?)";
        template.update(sql, material.getSubjectId(), material.getMaterialId(), material.getTopicName(),
                material.getDifficulty(), material.getDescription(), material.getFilename());
    }

    @Override
    public List<StudyMaterial> getAllBySubjectId(String subjectId) {
        String sql = "SELECT * FROM StudyMaterial WHERE subjectId = ?";
        List<StudyMaterial> materials = template.query(sql, new Object[] { subjectId },
                new BeanPropertyRowMapper<>(StudyMaterial.class));
        return materials;
    }

    @Override
    public StudyMaterial get(String subjectId, String materialId) {
        try {
            String sql = "SELECT * FROM StudyMaterial WHERE subjectId = ? AND materialId = ?";
            StudyMaterial material = template.queryForObject(sql, new Object[] { subjectId, materialId },
                    new BeanPropertyRowMapper<>(StudyMaterial.class));
            return material;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public String getFilename(String subjectId, String materialId) {
        try {
            String sql = "SELECT filename FROM StudyMaterial WHERE subjectId = ? AND materialId = ?";
            String filename = template.queryForObject(sql, new Object[] { subjectId, materialId }, String.class);
            return filename;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Update all attributes except filename, materialId, subjectId
     */
    @Override
    public void update(StudyMaterial material) {
        String sql = "UPDATE StudyMaterial SET topicName = ?, difficulty = ?, description = ? WHERE subjectId = ? AND materialId = ?";
        template.update(sql, material.getTopicName(), material.getDifficulty(), material.getDescription(),
                material.getSubjectId(), material.getMaterialId());
    }

    @Override
    public void delete(String subjectId, String materialId) {
        String sql = "DELETE FROM StudyMaterial WHERE subjectId = ? AND materialId = ?";
        template.update(sql, subjectId, materialId);
    }

}
