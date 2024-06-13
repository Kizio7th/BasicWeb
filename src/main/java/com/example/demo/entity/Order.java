package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "order_book")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "orderer")
	@JsonIgnore
	private User orderer;

	@ManyToOne
	@JoinColumn(name = "book")
	@JsonIgnore
	private Book book;

	@Column(name = "quantity")
	private Long quantity;

	@Column(name = "totalPrice")
	private Float totalPrice;

	@ManyToOne
	@JoinColumn(name = "bill")
	@JsonIgnore
	private Bill bill;

	@Column(name = "status")
	private Boolean status = false;
}