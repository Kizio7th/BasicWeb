package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Book;
import com.example.demo.entity.User;



public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByUserId(Long userId);
    List<Book> findByTitleAndAuthorAndUser(String title, String author, User user);
    Optional<Book> findById(Long id);
    void deleteById(Long id);
    
}