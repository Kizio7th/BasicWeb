package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.DashBoardDto;
import com.example.demo.entity.Bill;
import com.example.demo.entity.Book;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class Dashboard {
    private UserRepository userRepository;
    private BookRepository bookRepository;
    private BillRepository billRepository;
    private ReviewRepository reviewRepository;

    @GetMapping("/dashboard")
    public DashBoardDto dashboard() {
        try {
            DashBoardDto dashBoardDto = new DashBoardDto();
            Long bookCount = (long) 0;
            dashBoardDto.setUserCount(userRepository.count() - 1);
            List<BookDto> bookDtos = new ArrayList<>();
            for (Book book : bookRepository.findAll()) {
                BookDto bookDto = new BookDto();
                bookDto.setTitle(book.getTitle());
                bookDto.setSold(book.getSold());
                bookDto.setPrice(book.getPrice());
                bookDtos.add(bookDto);
                bookCount += book.getInStock();
            }
            dashBoardDto.setBooks(null);
            List<Bill> bills = billRepository.findAll();
            Float totalRevenue = (float) 0;
            Long totalBought = (long) 0;
            dashBoardDto.setPaidCount((long) bills.size());
            dashBoardDto.setBookCount(bookCount);

            dashBoardDto.setBoughtCount(totalBought);
            dashBoardDto.setTotalRevenue(totalRevenue);
            dashBoardDto.setBills(bills);
            return dashBoardDto;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
}
