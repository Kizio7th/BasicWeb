package com.example.demo.dto;

import java.sql.Date;


public class BookDto {
	private Long id;
	private String title;
	private String author;
	private String category;
	private Date releaseDate;
	private String pageNumber ;
	private Long sold;
	private String cover;
	private String description;
	private Long rating;
	public BookDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BookDto(Long id, String title, String author, String category, Date releaseDate, String pageNumber,
			Long sold, String cover, String description, Long rating) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.category = category;
		this.releaseDate = releaseDate;
		this.pageNumber = pageNumber;
		this.sold = sold;
		this.cover = cover;
		this.description = description;
		this.rating = rating;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Long getSold() {
		return sold;
	}
	public void setSold(Long sold) {
		this.sold = sold;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getRating() {
		return rating;
	}
	public void setRating(Long rating) {
		this.rating = rating;
	}
	
	
}
