package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Review;
import com.example.demo.entity.Book;


public interface ReviewRepository extends JpaRepository<Review, Long>{
	List<Review> findByBook(Book book);
	long countByBook(Book book);
}
