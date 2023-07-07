package com.example.demo.dto;

public class OrderDto {
	private Long id;
	private Long ordererId;
	private String ordererName;
	private Long bookId;
	private String bookName;
	private Long quantity;
	private String image;
	public OrderDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderDto(Long id, Long ordererId, String ordererName, Long bookId, String bookName, Long quantity,
			String image) {
		super();
		this.id = id;
		this.ordererId = ordererId;
		this.ordererName = ordererName;
		this.bookId = bookId;
		this.bookName = bookName;
		this.quantity = quantity;
		this.image = image;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrdererId() {
		return ordererId;
	}
	public void setOrdererId(Long ordererId) {
		this.ordererId = ordererId;
	}
	public String getOrdererName() {
		return ordererName;
	}
	public void setOrdererName(String ordererName) {
		this.ordererName = ordererName;
	}
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	
	
}
