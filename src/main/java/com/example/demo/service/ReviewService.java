package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ReviewDto;
import com.example.demo.entity.Review;

public interface ReviewService {
	void saveReview(ReviewDto reviewDto);
	Review findReviewById(Long id);
	void deleteReviewById(Long id);
	List<ReviewDto> findReviewsByBook(Long id);
	ReviewDto mapToReviewDto(Review review);
	
}
