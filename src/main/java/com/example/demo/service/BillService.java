package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Bill;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BillService {

    private BookRepository bookRepository;
	private UserRepository userRepository;
	private OrderRepository orderRepository;
	private BillRepository billRepository;

	public List<Bill> findAllBills() {
		return billRepository.findAll();
	}
}
