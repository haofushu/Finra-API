package com.example.demo.Controller;


import com.example.demo.Entity.File;
import com.example.demo.Service.FileService;
import com.example.demo.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;

/*
• Write a scheduler in the same app to poll for new items in the last hour and send an email (Optional)*/
@RestController
@RequestMapping("/file")
public class MyController {

    @Autowired
    private FileService fileService;

    @Autowired
    private MailService mailService;

    @Value("${sendTo}")
    String sendTo;

    @Value("${topic}")
    String topic;

    @GetMapping()
    public List<File> getFile(){
        return fileService.getAllFiles();
    }

    @GetMapping(value = "/meta-data/{id}")
    public Optional<File> getById(@PathVariable Integer id){
        return fileService.findById(id);
    }

    @GetMapping(value = "/search/{name}")
    public Integer getByName(@PathVariable String name){
        Optional<File> f = fileService.findIdByName(name);
        return f==null?-1:f.get().getId();
    }

    @PostMapping
    public String postFile(@RequestBody MultipartFile file){
        return fileService.uploadFile(file);
    }

    @GetMapping(value = "/download/{id}")
    public void downloadFile(@PathVariable Integer id, HttpServletResponse response, HttpServletRequest request)
            throws IOException {
        Optional<File> file = fileService.findById(id);
        if(file == null){
            throw new IllegalArgumentException("wrong id number");
        }
        String mediaType = URLConnection.guessContentTypeFromName(file.get().getName());
        if(mediaType==null){
            mediaType = "application/octet-stream";
        }
        InputStream inputStream = new FileInputStream(file.get().getPath());
        response.addHeader("Content-disposition", "attachment; filename=\""+ file.get().getName()+"\"" );
        response.setContentType(mediaType);
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    @Scheduled(fixedDelay = 600)
    public void sendEmail() throws MessagingException {
        String body = getBody();
        mailService.send(sendTo, topic, body);
    }

    private String getBody() {
        StringBuilder sb = new StringBuilder();
        List<File> Files = fileService.getRecent();
        for (File file : Files) {
            sb.append(file.toString()+"\r\n");
        }
        return sb.toString();
    }
}
