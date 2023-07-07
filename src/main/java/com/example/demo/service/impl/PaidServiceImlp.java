package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Paid;
import com.example.demo.repository.PaidRepository;
import com.example.demo.service.PaidService;

@Service
public class PaidServiceImlp implements PaidService {
	
	private PaidRepository paidRepository;
	
	public PaidServiceImlp(PaidRepository paidRepository) {
		super();
		this.paidRepository = paidRepository;
	}

	@Override
	public List<Paid> findAllPaids() {
		return paidRepository.findAll();
	}

}
