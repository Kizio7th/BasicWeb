package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Paid {
	private Long id;

	private String purchaser;

	private String cart;

	private Float totalPrice;

	private String time;

}
