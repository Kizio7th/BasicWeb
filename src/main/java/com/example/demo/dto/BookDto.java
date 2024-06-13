package com.example.demo.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
	private Long id;
	private String title;
	private String author;
	private Float price;
	private String category;
	private Date releaseDate;
	private String pageNumber;
	private Long sold;
	private String cover;
	private String description;
	private Long rating;

}
