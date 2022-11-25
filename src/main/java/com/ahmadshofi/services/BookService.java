package com.ahmadshofi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ahmadshofi.entity.Book;
import com.ahmadshofi.repository.BookRepository;
import com.ahmadshofi.utility.CSVHelper;

@Service("bookService")
@Transactional
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;

    public List<Book> save(MultipartFile file) {
        try {
            List<Book> books = CSVHelper.csvToBooks(file.getInputStream());
            return bookRepository.saveAll(books);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
