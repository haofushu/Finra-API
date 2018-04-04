package com.example.demo.Service;

import com.example.demo.Entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface FileService {
    List<File> getAllFiles();
    String uploadFile(MultipartFile file);
    Optional<File> findById(Integer id);
    Optional<File> findIdByName(String name);
    List<File> getRecent();
}
