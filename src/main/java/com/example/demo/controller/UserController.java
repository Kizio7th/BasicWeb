package com.example.demo.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.OrderDto;
import com.example.demo.dto.ReviewDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.service.BookService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ReviewService;
import com.example.demo.service.UserService;

@Controller
public class UserController {

	private UserService userService;
	private BookService bookService;
	private ReviewService reviewService;
	private OrderService orderService;

	public UserController(UserService userService, BookService bookService, ReviewService reviewService,
			OrderService orderService) {
		super();
		this.userService = userService;
		this.bookService = bookService;
		this.reviewService = reviewService;
		this.orderService = orderService;
	}

	@ModelAttribute("currentUser")
	public User currentUser(Authentication auth) {
		if (auth == null) {
			return new User();
		}
		User currentUser = userService.findUserByEmail(auth.getName());
		return currentUser;
	}

	@ModelAttribute("isAdmin")
	public Boolean isAdmin(Authentication auth) {
		if (auth == null) {
			return false;
		}
		for (GrantedAuthority authority : auth.getAuthorities()) {
			if (authority.getAuthority().equals("ROLE_ADMIN")) {
				return true;
			}
		}
		return false;
	}

	@GetMapping("/user/{id}")
	public String user(Model model, @PathVariable("id") Long id, Authentication auth) {
		try {
			User currentUser = currentUser(auth);
			User user = userService.findUserById(id);
			List<OrderDto> orders = orderService.findOrdersByOrdererId(id);
			if (currentUser.getId() == id) {
				model.addAttribute("user", user);
				model.addAttribute("orders", orders);
				return "user";
			} else
				return "redirect:/";
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/";
	}

	@PostMapping("/user/save")
	public String updateProfile(Model model, UserDto userDto, @RequestParam(name = "id") Long id, Authentication auth) {
		userService.updateUser(userDto, id);
		return "redirect:/user/" + String.valueOf(id);
	}

	@GetMapping("/user/{id}/changePassword")
	public String showChangePasswordForm(Model model, UserDto userDto, Authentication auth) {
		User user = userService.findUserById(userDto.getId());
		User currentUser = currentUser(auth);
		if (currentUser.getId() == userDto.getId()) {
			model.addAttribute("user", user);
			return "change-password";
		} else {
			return "redirect:/";
		}

	}

	@PostMapping("/user/changePassword")
	public String changePassword(Model model, UserDto userDto, @RequestParam(name = "id") Long id,
			Authentication auth) {
		userService.changePassword(userDto, id);
		return "redirect:/user/" + String.valueOf(id);
	}

	@PostMapping("/user/delete/{id}")
	public String deleteUser(Model model, @PathVariable("id") Long id, Authentication auth) {
		User currentUser = currentUser(auth);
		if (currentUser.getId() != id) {
			return "redirect:/";
		}
		for (Order order : currentUser.getOrders()) {
			orderService.deleteOrder(order.getId());
		}
		userService.deleteUser(id);
		return "redirect:/logout";
	}

	@GetMapping("/user/book/{id}")
	public String book(Model model, @PathVariable("id") Long id, Authentication auth) {
		Book book = bookService.findBookById(id);
		bookService.increaseView(id);
		BookDto bookdto = bookService.mapToBookDto(book);
		List<ReviewDto> reviews = reviewService.findReviewsByBook(id);
		Collections.reverse(reviews);
		model.addAttribute("book", bookdto);
		model.addAttribute("reviews", reviews);
		return "rating";
	}

	@PostMapping("/user/bookOrder/{id}")
	public String orderBook(Model model, @ModelAttribute("order") OrderDto orderDto, @PathVariable("id") Long id,
			Authentication auth) {
		User currentUser = currentUser(auth);
		orderDto.setOrdererId(currentUser.getId());
		orderDto.setBookId(id);
		orderService.orderBook(orderDto);
		BookDto book = bookService.mapToBookDto(bookService.findBookById(id));
		List<ReviewDto> reviews = reviewService.findReviewsByBook(id);
		Collections.reverse(reviews);
		model.addAttribute("book", book);
		model.addAttribute("reviews", reviews);
		model.addAttribute("msg", "Đã thêm đơn hàng");
		return "rating";
	}

	@PostMapping("/user/order/delete/{id}")
	public String deleteOrder(Model model, @PathVariable("id") Long id, Authentication auth) {
		User currentUser = currentUser(auth);
		if (currentUser.getId() != id) {
			return "redirect:/";
		}
		orderService.deleteOrder(id);
		User user = userService.findUserById(id);
		List<OrderDto> orders = orderService.findOrdersByOrdererId(id);
		model.addAttribute("user", user);
		model.addAttribute("orders", orders);
		model.addAttribute("msg", "Đã xoá đơn hàng");
		return "user";
	}

	@PostMapping("/user/review/{id}")
	public String reviewBook(Model model, @ModelAttribute("review") ReviewDto reviewDto, @PathVariable("id") Long id,
			Authentication auth) {
		User currentUser = currentUser(auth);
		reviewDto.setReviewerId(currentUser.getId());
		reviewDto.setBookId(id);
		reviewService.saveReview(reviewDto);
		return "redirect:/user/book/" + id.toString();
	}

	@PostMapping("/user/review/delete/{id}")
	public String deleteReview(Model model, @PathVariable("id") Long id, Authentication auth) {
		Book book = reviewService.findReviewById(id).getBook();
		reviewService.deleteReviewById(id);
		return "redirect:/user/book/" + book.getId();
	}

	@PostMapping("/user/paid/{id}")
	public String paid(Model model, @PathVariable("id") Long id, Authentication auth) {
		User currentUser = currentUser(auth);
		if (currentUser.getId() != id) {
			return "redirect:/";
		}
		orderService.paid(id);
		User user = userService.findUserById(id);
		List<OrderDto> orders = orderService.findOrdersByOrdererId(id);
		model.addAttribute("user", user);
		model.addAttribute("orders", orders);
		model.addAttribute("msg", "Đã thanh toán thành công");
		return "user";
	}
}
