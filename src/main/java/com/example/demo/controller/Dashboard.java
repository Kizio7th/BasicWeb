package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.DashBoardDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.Paid;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.PaidRepository;
import com.example.demo.repository.UserRepository;

@RestController
@CrossOrigin(origins = "*")
public class Dashboard {
    private UserRepository userRepository;
    private BookRepository bookRepository;
    private PaidRepository paidRepository;

    public Dashboard(UserRepository userRepository, BookRepository bookRepository, PaidRepository paidRepository) {
        super();
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.paidRepository = paidRepository;
    }

    @GetMapping("/dashboard")
    public DashBoardDto dashboard() {
        try {
            DashBoardDto dashBoardDto = new DashBoardDto();
            dashBoardDto.setBookCount(bookRepository.count());
            dashBoardDto.setUserCount(userRepository.count() - 1);
            List<BookDto> bookDtos = new ArrayList<>();
            for (Book book : bookRepository.findAll()) {
                BookDto bookDto = new BookDto();
                bookDto.setTitle(book.getTitle());
                bookDto.setSold(book.getSold());
                bookDto.setPrice(book.getPrice());
                bookDtos.add(bookDto);
            }
            dashBoardDto.setBooks(bookDtos);
            List<Paid> paids = paidRepository.findAll();
            Float totalRevenue = (float) 0;
            Long totalBought = (long) 0;
            for (Paid paid : paids) {
                String cart = paid.getCart();
                String[] orders = cart.split("; ");
                for (String order : orders) {
                    Long quantity = Long.parseLong(order.split(" _ ")[2]);
                    totalBought += quantity;
                }
                totalRevenue += paid.getTotalPrice();
            }
            dashBoardDto.setBoughtCount(totalBought);
            dashBoardDto.setTotalRevenue(totalRevenue);
            dashBoardDto.setPaids(paids);
            return dashBoardDto;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
}
