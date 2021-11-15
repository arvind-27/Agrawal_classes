package com.example.agrawal_classes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Transactional
@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private ServletContext context;

    private final Path uploadDirectory = Paths.get("src", "main", "webapp", "uploads");

    @Override
    public void save(String subjectId, MultipartFile file) {
        try {
            Path uploadLocation = this.uploadDirectory.resolve(subjectId);
            Files.createDirectories(uploadLocation);

            Files.copy(file.getInputStream(), uploadLocation.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Unable to save the file. Error: " + e.getMessage());
        }
    }

    @Override
    public String getFileLocation(String subjectId, String filename) {
        String path = context.getRealPath("uploads/") + subjectId + "/" + filename;
        return path;
    }

    @Override
    public Path getFilePath(String subjectId, String filename) {
        Path path = this.uploadDirectory.resolve(subjectId).resolve(filename);
        return path;
    }

    @Override
    public void delete(String subjectId, String filename) {
        try {
            Path uploadLocation = this.uploadDirectory.resolve(subjectId);

            Path file = uploadLocation.resolve(filename);
            Files.delete(file);
        } catch (Exception e) { }
    }

}
