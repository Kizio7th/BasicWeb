package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
	private Long id;
	private Long ordererId;
	private String ordererName;
	private Long bookId;
	private String bookName;
	private Long quantity;
	private String image;
	private Float totalPrice;
	private String time;
}
