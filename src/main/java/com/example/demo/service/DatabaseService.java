package com.example.demo.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.FileStorageException;
import com.example.demo.models.Image;
import com.example.demo.repository.DatabaseFileRepository;

@Service
public class DatabaseService {
	
	 @Autowired
	    private DatabaseFileRepository  dbFileRepository;

	    public Image storeFile(MultipartFile file) {
	        // Normalize file name
	        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	        try {
	            // Check if the file's name contains invalid characters
	            if (fileName.contains("..")) {
	                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	            }

	            Image dbFile = new Image(fileName, file.getContentType(), file.getBytes());

	            return dbFileRepository.save(dbFile);
	        } catch (IOException ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }
	    }

	    
	    public Image getFile(String fileId) {
	    	
	    	
	    	return dbFileRepository.findById(fileId).get();  	

	    }
	    
	    
        public List<Image>  getAllFile() { 	
	    	return dbFileRepository.findAll(); 	
	    }

}
