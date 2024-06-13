package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.OrderDto;
import com.example.demo.dto.Paid;
import com.example.demo.entity.Bill;
import com.example.demo.entity.Order;


public interface OrderService {
	Order findById(Long id);
	void orderBook(OrderDto orderDto);
	void deleteOrder(Long id);
	List<OrderDto> findOrdersByOrdererId(Long id);
	OrderDto mapToOrderDto(Order order);
	void paid (Long userId);
	Long countOrder();
	List<Paid> BillToPair(List<Bill> bills);
}
