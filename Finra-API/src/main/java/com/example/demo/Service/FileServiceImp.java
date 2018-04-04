package com.example.demo.Service;

import com.example.demo.DAO.FileRepository;
import com.example.demo.Entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImp implements FileService{
    private static String file_folder ="src/main/resources/files/";

    @Autowired
    private FileRepository fileRepository;

    @Override
    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }

    @Override
    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return "fileNotFound";
        }
        try{
            byte[] bytes = file.getBytes();
            String name = file.getOriginalFilename();
            Date date = new Date();
            Path path = Paths.get(file_folder + file.getOriginalFilename());
            Files.write(path, bytes);
            File f= new File(name,date, path.toString());
            fileRepository.save(f);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "Success";
    }

    @Override
    public Optional<File> findById(Integer id) {
        return fileRepository.findById(id);
    }

    @Override
    public Optional<File> findIdByName(String name) {
        return fileRepository.findIdByName(name);
    }

    @Override
    public List<File> getRecent() {
        return fileRepository.getRecent();
    }
}
