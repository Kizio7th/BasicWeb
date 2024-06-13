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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

	@Column(name = "price")
	private Float price;

	@Column(name = "category")
	private String category;

	@Column(name = "release date")
	private Date releaseDate;

	@Column(name = "page number")
	private String pageNumber;

	@Column(name = "sold")
	private Long sold;

	@Column(name = "cover", unique = false, nullable = false, length = 100000)
	private byte[] cover;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
	private List<Review> reviews = new ArrayList<>();

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
	private List<Order> orders = new ArrayList<>();

	@Column(name = "description", length = 1000)
	private String description;

	@Column(name = "inStock")
	private Long inStock = (long) 1000;
}