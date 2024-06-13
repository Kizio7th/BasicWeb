package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Bill;
import com.example.demo.repository.BillRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BillService {

	private BillRepository billRepository;

	public List<Bill> findAllBills() {
		return billRepository.findAll();
	}
}
