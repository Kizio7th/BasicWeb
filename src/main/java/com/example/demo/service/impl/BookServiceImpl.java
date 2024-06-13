package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.BookDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BookService;
import com.example.demo.util.ImageUtil;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService{
	
	private BookRepository bookRepository;
	private UserRepository userRepository;

	@Override
	public void saveBook(BookDto bookDto, MultipartFile bookImage, User user) {
		Book book = new Book();
		book.setTitle(bookDto.getTitle());
		book.setPrice(bookDto.getPrice());
		book.setAuthor(bookDto.getAuthor());
		book.setCategory(bookDto.getCategory());
		book.setReleaseDate(bookDto.getReleaseDate());
		book.setPageNumber(bookDto.getPageNumber());
		book.setSold(Long.valueOf(0));
		book.setCover(ImageUtil.compressImage(bookImage));
		book.setUser(user);
		book.setDescription(bookDto.getDescription());
		bookRepository.save(book);
		
	}
	
	@Override
	public void orderBook(BookDto bookDto, Long quantiy, User user) {
		Book book = new Book();
		book.setTitle(bookDto.getTitle());
		book.setPrice(bookDto.getPrice());
		book.setAuthor(bookDto.getAuthor());
		book.setCategory(bookDto.getCategory());
		book.setReleaseDate(bookDto.getReleaseDate());
		book.setPageNumber(bookDto.getPageNumber());
		book.setSold(quantiy);
		book.setUser(user);
		book.setDescription(bookDto.getDescription());
		bookRepository.save(book);
	}

	@Override
	public void updateBook(BookDto bookDto, MultipartFile bookImage, Long id) {
		Book book = bookRepository.findById(id).orElse(null);
		book.setTitle(bookDto.getTitle());
		book.setAuthor(bookDto.getAuthor());
		book.setPrice(bookDto.getPrice());
		book.setCategory(bookDto.getCategory());
		book.setReleaseDate(bookDto.getReleaseDate());
		book.setPageNumber(bookDto.getPageNumber());
		if (bookImage.getSize() != 0) book.setCover(ImageUtil.compressImage(bookImage));
		book.setDescription(bookDto.getDescription());
		bookRepository.save(book);
	}
	@Override
	public Book findBookById(Long id) {
		return bookRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<BookDto> findBooksByTitleAndAuthor(String title, String author) {
		List<Book> books = bookRepository.findByTitleAndAuthorAndUser(title, author,userRepository.findById(Long.valueOf(1)).orElse(null));
		return books.stream()
				.map((book) -> mapToBookDto(book))
				.collect(Collectors.toList());
	}
	@Override
	public List<BookDto> findBooksByUserId(Long id) {
		List<Book> books = bookRepository.findByUserId(id);
		return books.stream()
				.map((book) -> mapToBookDto(book))
				.collect(Collectors.toList());
	}
	
	
	@Override
	public List<BookDto> findAllBooks() {
		List<Book> books = bookRepository.findAll();
		return books.stream()
				.map((book) -> mapToBookDto(book))
				.collect(Collectors.toList());
	}
	@Override
	public BookDto mapToBookDto(Book book) {
		BookDto bookDto = new BookDto();
		bookDto.setId(book.getId());
		bookDto.setTitle(book.getTitle());
		bookDto.setPrice(book.getPrice());
		bookDto.setAuthor(book.getAuthor());
		bookDto.setCategory(book.getCategory());
		bookDto.setReleaseDate(book.getReleaseDate());
		bookDto.setPageNumber(book.getPageNumber());
		bookDto.setSold(book.getSold());
		bookDto.setCover(ImageUtil.decompressImage(book.getCover()));
		bookDto.setDescription(book.getDescription());
		long averageRating = 0;
		List<Review> reviews = book.getReviews();
		for (Review review : reviews) {
			averageRating += review.getRating();
        }
		if (reviews.size() > 0) averageRating /= reviews.size();
		else averageRating = 5;
		bookDto.setRating(Long.valueOf(averageRating));
		return bookDto;
	}

	@Override
	public String handleMultipartFile(MultipartFile image) {
		return ImageUtil.decompressImage(ImageUtil.compressImage(image));
	}

	@Override
	public String handleByte(byte[] image) {
		return ImageUtil.decompressImage(image);
	}
	@Override
	public void deleteBook(Long id) {
		Book book = bookRepository.findById(id).orElse(null);
		bookRepository.deleteById(book.getId());
		
	}

	@Override
	public void increaseView(Long id) {
		Book book = bookRepository.findById(id).orElse(null);
		book.setView(book.getView() + 1);
		bookRepository.save(book);
	}

	

	

	

	

	

}
