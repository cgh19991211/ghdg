package com.gh.filecenter.controller;

import com.gh.filecenter.entities.File;
import com.gh.filecenter.service.FileService;
import com.gh.filecenter.utils.Result;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/file")
public class FileController {
    
    @Autowired
    private FileService fileService;
    
    @GetMapping("/image/{id}")
    public byte[] getImage(@PathVariable("id") String id){
        byte[] data = null;
        File file = fileService.getFileById(id);
        if(file!=null){
            data = file.getContent().getData();
        }
        return data;
    }
    
    @PostMapping("/image/save")
    public String saveImage(@RequestParam(value = "image")MultipartFile multipartFile){
        try {
            File file = new File();
            file.setName(multipartFile.getOriginalFilename());
            file.setContentType(multipartFile.getContentType());
            file.setContent(new Binary(multipartFile.getBytes()));
            file.setSize(multipartFile.getSize());
            File saveFile = fileService.saveFile(file);
            String url = "http://localhost:8080/file/image/"+saveFile.getId();
            return url;
        }catch (IOException e){
            return null;
        }
    }
}
