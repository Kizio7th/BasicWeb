package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.BookDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.User;

public interface BookService {
	void saveBook(BookDto bookDto, MultipartFile bookImage, User user) ;
	Book findBookById(Long id);
	List<BookDto> findBooksByTitleAndAuthor(String title, String author);
	List<BookDto> findBooksByUserId(Long id);
	List<BookDto> findAllBooks();
	void updateBook(BookDto bookDto, MultipartFile bookImage, Long id);
	BookDto mapToBookDto(Book book);
	void deleteBook(Long id);
	String handleMultipartFile(MultipartFile image);
	String handleByte(byte[] image);
	void orderBook(BookDto bookDto, Long quantiy, User user);
	void increaseView(Long id);
}
