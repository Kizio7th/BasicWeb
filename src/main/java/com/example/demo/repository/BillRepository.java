package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Bill;
import com.example.demo.entity.User;

public interface BillRepository extends JpaRepository<Bill, Long>{
	List<Bill> findByUser(User user);
}
