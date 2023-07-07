package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.OrderDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.Order;
import com.example.demo.entity.Paid;
import com.example.demo.entity.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PaidRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OrderService;
import com.example.demo.util.ImageUtil;

@Service
public class OrderServiceImpl implements OrderService {
	
	private BookRepository bookRepository;
	private UserRepository userRepository;
	private OrderRepository orderRepository;
	private PaidRepository paidRepository;
	
	

	public OrderServiceImpl(BookRepository bookRepository, UserRepository userRepository,
			OrderRepository orderRepository, PaidRepository paidRepository) {
		super();
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
		this.orderRepository = orderRepository;
		this.paidRepository = paidRepository;
	}

	@Override
	public void orderBook(OrderDto orderDto) {
		User user = userRepository.findById(orderDto.getOrdererId()).orElse(null);
		for(Order order : user.getOrders()) {
			if(order.getBook().getId() == (orderDto.getBookId())) {
				order.setQuantity(orderDto.getQuantity());
				orderRepository.save(order);
				return;
			}
		}
		Order order = new Order();
		Book book = bookRepository.findById(orderDto.getBookId()).orElse(null);
		order.setOrderer(user);
		order.setBook(book);
		order.setQuantity(orderDto.getQuantity());
		orderRepository.save(order);
	}

	@Override
	public void deleteOrder(Long id) {
		Order order = orderRepository.findById(id).orElse(null);
		orderRepository.delete(order);
	}

	@Override
	public List<OrderDto> findOrdersByOrdererId(Long id) {
		List<Order> orders = orderRepository.findByOrderer(userRepository.findById(id).orElse(null));
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
		orderDto.setImage(ImageUtil.decompressImage(order.getBook().getCover()));
		return orderDto;
	}

	@Override
	public void paid(Long userId) {
		Paid paid = new Paid();
		User user = userRepository.findById(userId).orElse(null);
		paid.setPurchaser(user.getId() + " - " + user.getName() + " - "+ user.getEmail());
		String cart = "";
		List<Order> orders = orderRepository.findByOrderer(userRepository.findById(userId).orElse(null));
		for (Order order : orders) {
			Book book = order.getBook();
			book.setSold(book.getSold() + order.getQuantity());
			cart += book.getTitle() + " _ " + book.getAuthor() + " _ " + order.getQuantity() + "; ";
			bookRepository.save(book);
			orderRepository.delete(order);
	    }
		paid.setCart(cart);
		LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
		paid.setTime(currentTime.format(formatter));
		paidRepository.save(paid);
	}
}
