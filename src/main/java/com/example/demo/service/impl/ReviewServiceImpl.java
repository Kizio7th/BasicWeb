package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ReviewDto;
import com.example.demo.entity.Review;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ReviewService;


@Service
public class ReviewServiceImpl implements ReviewService {
	
	private ReviewRepository reviewRepository;
	private UserRepository userRepository;
	private BookRepository bookRepository;
	
	
	
	public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository,
			BookRepository bookRepository) {
		super();
		this.reviewRepository = reviewRepository;
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
	}

	@Override
	public void saveReview(ReviewDto reviewDto) {
		Review review = new Review();
		review.setRating(reviewDto.getRating());
		review.setComment(reviewDto.getComment());
		review.setReviewer(userRepository.findById(reviewDto.getReviewerId()).orElse(null));
		review.setBook(bookRepository.findById(reviewDto.getBookId()).orElse(null));
		reviewRepository.save(review);
	}
	
	@Override
	public List<ReviewDto> findReviewsByBook(Long id) {
		List<Review> reviews = reviewRepository.findByBook(bookRepository.findById(id).orElse(null));
		return reviews.stream()
				.map((review) -> mapToReviewDto(review))
				.collect(Collectors.toList());
	}
	
	@Override
	public Review findReviewById(Long id) {
		return reviewRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteReviewById(Long id) {
		reviewRepository.delete(reviewRepository.findById(id).orElse(null));
	}
	
	@Override
	public ReviewDto mapToReviewDto(Review review) {
		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setId(review.getId());
		reviewDto.setBookId(review.getBook().getId());
		reviewDto.setBookName(review.getBook().getTitle());
		reviewDto.setReviewerId(review.getReviewer().getId());
		reviewDto.setReviewerName(review.getReviewer().getName());
		reviewDto.setRating(review.getRating());
		reviewDto.setComment(review.getComment());
		return reviewDto;
	}
	
}
