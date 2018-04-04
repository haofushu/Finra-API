package com.example.demo.DAO;

import com.example.demo.Entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File,Integer> {
    List<File> getRecent();
    Optional<File> findIdByName( String name);
    Optional<File> findById(Integer id);
}
