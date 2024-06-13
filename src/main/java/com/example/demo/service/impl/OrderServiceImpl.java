package com.example.demo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.OrderDto;
import com.example.demo.dto.Paid;
import com.example.demo.entity.Bill;
import com.example.demo.entity.Book;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OrderService;
import com.example.demo.util.ImageUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

	private BookRepository bookRepository;
	private UserRepository userRepository;
	private OrderRepository orderRepository;
	private BillRepository billRepository;

	@Override
	public void orderBook(OrderDto orderDto) {
		User user = userRepository.findById(orderDto.getOrdererId()).orElse(null);
		for (Order order : user.getOrders()) {
			if (order.getBook().getId() == (orderDto.getBookId()) && order.getStatus() == false) {
				Long quantity = orderDto.getQuantity() + order.getQuantity();
				order.setQuantity(quantity);
				order.setTotalPrice(quantity * order.getBook().getPrice());
				orderRepository.save(order);
				return;
			}
		}
		Order order = new Order();
		Book book = bookRepository.findById(orderDto.getBookId()).orElse(null);
		order.setOrderer(user);
		order.setBook(book);
		order.setQuantity(orderDto.getQuantity());
		order.setTotalPrice(book.getPrice() * orderDto.getQuantity());
		orderRepository.save(order);
	}

	@Override
	public void deleteOrder(Long id) {
		Order order = orderRepository.findById(id).orElse(null);
		orderRepository.delete(order);
	}

	@Override
	public Long countOrder() {
		return orderRepository.countByStatus(false);
	}

	@Override
	public List<OrderDto> findOrdersByOrdererId(Long id) {
		List<Order> orders = orderRepository.findByOrdererAndStatus(userRepository.findById(id).orElse(null), false);
		return orders.stream()
				.map((order) -> mapToOrderDto(order))
				.collect(Collectors.toList());
	}

	@Override
	public Order findById(Long id) {
		return orderRepository.findById(id).orElse(null);
	}

	@Override
	public OrderDto mapToOrderDto(Order order) {
		OrderDto orderDto = new OrderDto();
		orderDto.setId(order.getId());
		orderDto.setBookId(order.getBook().getId());
		orderDto.setBookName(order.getBook().getTitle());
		orderDto.setOrdererId(order.getOrderer().getId());
		orderDto.setOrdererName(order.getOrderer().getName());
		orderDto.setQuantity(order.getQuantity());
		orderDto.setTotalPrice(order.getTotalPrice());
		orderDto.setImage(ImageUtil.decompressImage(order.getBook().getCover()));
		return orderDto;
	}

	@Override
	public void paid(Long userId) {
		
		User user = userRepository.findById(userId).orElse(null);
		List<Order> orders = orderRepository.findByOrdererAndStatus(userRepository.findById(userId).orElse(null),
				false);
		Float totalPrice = (float) 0;
		Bill bill = new Bill();
		bill.setUser(user);
		bill.setTime(new Date());
		bill = billRepository.save(bill);
		for (Order order : orders) {
			Book book = order.getBook();
			book.setSold(book.getSold() + order.getQuantity());
			book.setInStock(book.getInStock() - order.getQuantity());
			bookRepository.save(book);
			order.setStatus(true);
			order.setBill(bill);
			orderRepository.save(order);
			totalPrice += order.getTotalPrice();
		}
		bill.setTotalPrice(totalPrice);
		billRepository.save(bill);
	}

	@Override
	public List<Paid> BillToPair(List<Bill> bills) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
		List<Paid> paids = new ArrayList<>();
		for (Bill bill : bills) {
			Paid paid = new Paid();
			paid.setPurchaser(
					bill.getUser().getId() + " - " + bill.getUser().getName() + " - " + bill.getUser().getEmail());
			String cart = "";
			List<Order> orders = orderRepository.findByBill(bill);
			for (Order order : orders) {
				Book book = order.getBook();
				cart += book.getTitle() + " _ " + book.getAuthor() + " _ " + order.getQuantity() + "; ";
			}
			paid.setCart(cart);
			paid.setTotalPrice(bill.getTotalPrice());
			paid.setTime(formatter.format(bill.getTime()));
			paids.add(paid);
		}
		return paids;
	}

}
