package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.model.StudyMaterial;

import java.util.List;

public interface StudyMaterialDao {
    public void save(StudyMaterial material);

    public List<StudyMaterial> getAllBySubjectId(String subjectId);

    public StudyMaterial get(String subjectId, String materialId);

    public String getFilename(String subjectId, String materialId);

    public void update(StudyMaterial material);

    public void delete(String subjectId, String materialId);
}
