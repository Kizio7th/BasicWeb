package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Bill;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;


public interface OrderRepository extends JpaRepository<Order, Long>{
	List<Order> findByOrdererAndStatus(User user, Boolean status);
	Long countByStatus(Boolean status);
	void deleteByOrderer(User orderer);
    List<Order> findByBill(Bill bill);
}
