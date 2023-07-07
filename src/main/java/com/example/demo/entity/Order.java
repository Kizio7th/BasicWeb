package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_book")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
    @JoinColumn(name = "orderer")
    private User orderer;
	
	@ManyToOne
    @JoinColumn(name = "book")
    private Book book;
	
	@Column(name = "quantity")
	private Long quantity;
	
	

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(Long id, User orderer, Book book, Long quantity) {
		super();
		this.id = id;
		this.orderer = orderer;
		this.book = book;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getOrderer() {
		return orderer;
	}

	public void setOrderer(User orderer) {
		this.orderer = orderer;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
	
}