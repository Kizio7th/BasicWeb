package com.example.demo.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "author")
    private String author;
    
    @Column(name = "category")
    private String category;
    
    @Column(name = "release date")
    private Date releaseDate;
    
    @Column(name = "page number")
    private String pageNumber ;
    
    @Column(name = "sold")
    private Long sold;
    
    @Column(name = "cover", unique = false, nullable = false, length = 100000)
	private byte[] cover;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL )
    private List<Review> reviews = new ArrayList<>();
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL )
    private List<Order> orders = new ArrayList<>();
    
    @Column(name = "description",length = 1000)
    private String description;
    
	public Book() {
		super();
	}

	public Book(Long id, String title, String author, String category, Date releaseDate, String pageNumber, Long sold,
			byte[] cover, User user, List<Review> reviews, List<Order> orders, String description) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.category = category;
		this.releaseDate = releaseDate;
		this.pageNumber = pageNumber;
		this.sold = sold;
		this.cover = cover;
		this.user = user;
		this.reviews = reviews;
		this.orders = orders;
		this.description = description;
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

	public byte[] getCover() {
		return cover;
	}

	public void setCover(byte[] cover) {
		this.cover = cover;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}