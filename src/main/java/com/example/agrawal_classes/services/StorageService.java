package com.example.agrawal_classes.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {
    public void save(String subjectId, MultipartFile file);

    public String getFileLocation(String subjectId, String filename);

    public Path getFilePath(String subjectId, String filename);

    public void delete(String subjectId, String filename);
}
