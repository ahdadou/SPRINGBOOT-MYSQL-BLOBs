package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Image;
import com.example.demo.service.DatabaseService;


@RestController
@CrossOrigin("*")
public class FileDownloadController {

	
	@Autowired
    private DatabaseService fileStorageService;

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity < Resource > downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Image databaseFile = fileStorageService.getFile(fileName);
        
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(databaseFile.getFileType()))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + databaseFile.getFileName() + "\"")
            .body(new ByteArrayResource(databaseFile.getData()));
    }
    
    @GetMapping("/downloadAllFile")
    public ResponseEntity < Resource > downloadAllFile(HttpServletRequest request) {
    	// Load file as Resource
        List<Image> databaseFile = fileStorageService.getAllFile();
        List<ResponseEntity < Resource >> listByte=new ArrayList<ResponseEntity < Resource >>();
        
        for(int j=0;j<databaseFile.size();j++){  
        	listByte.add(ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(databaseFile.get(j).getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + databaseFile.get(j).getFileName() + "\"")
                    .body(new ByteArrayResource(databaseFile.get(j).getData())));
         }
         
        
        
        
        return listByte.get(1);
    }
    
    
    
    
}
