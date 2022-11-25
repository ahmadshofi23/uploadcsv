package com.ahmadshofi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ahmadshofi.dto.ResponseData;
import com.ahmadshofi.entity.Book;
import com.ahmadshofi.services.BookService;
import com.ahmadshofi.utility.CSVHelper;

@RestController
@RequestMapping("/books")
public class BookController {
    
    @Autowired
    private BookService bookService;

    @RequestMapping
    public ResponseEntity<?> findAllBooks() {
        ResponseData responseData = new ResponseData();

        try {
            List<Book> books = bookService.findAll();
            responseData.setStatus(true);
            responseData.getMessages().add("Success");
            responseData.setPayload(books);
            return ResponseEntity.ok(responseData);
        
        } catch (Exception e) {
            responseData.setStatus(false);
            responseData.getMessages().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }

    }

    @RequestMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        ResponseData responseData = new ResponseData();

        if(!CSVHelper.hasCSVFormat(file)) {
            responseData.setStatus(false);
            responseData.getMessages().add("Please upload a csv file!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        try {
            List<Book> books = bookService.save(file);
            responseData.setStatus(true);
            responseData.getMessages().add("Uploaded the file successfully: " + file.getOriginalFilename());
            responseData.setPayload(books);
            return ResponseEntity.ok(responseData);
        
        } catch (Exception e) {
            responseData.setStatus(false);
            responseData.getMessages().add("Could not upload the file: " + file.getOriginalFilename() + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }
}
