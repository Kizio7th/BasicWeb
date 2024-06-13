package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Bill;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;


public interface OrderRepository extends JpaRepository<Order, Long>{
	List<Order> findByOrdererAndStatus(User user, Boolean status);
	Long countByStatus(Boolean status);
	void deleteByOrderer(User orderer);
    List<Order> findByBill(Bill bill);

	@Query("SELECT p FROM Order p ORDER BY p.id DESC LIMIT 3")
	List<Order> findTop3NewestOrder();
}
