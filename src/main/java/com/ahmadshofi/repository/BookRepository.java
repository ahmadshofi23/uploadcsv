package com.ahmadshofi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahmadshofi.entity.Book;

public interface BookRepository  extends JpaRepository<Book, Long> {

}
    
