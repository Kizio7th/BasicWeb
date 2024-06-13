package com.example.demo.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.DashBoardDto;
import com.example.demo.entity.Bill;
import com.example.demo.entity.Book;
import com.example.demo.entity.Order;
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
            LocalDateTime now = LocalDateTime.now();
            int currentMonth = now.getMonthValue();
            int currentYear = now.getYear();
            DashBoardDto dashBoardDto = new DashBoardDto();
            Long totalBooksInStock = (long) 0;
            // Float totalRevenue = (float) 0;
            Long totalBooksSoldInMonth = (long) 0;
            Long views = (long) 0;
            Long totalTheBookSoldInMonth = (long) 0;
            Long totalTransactionInMonth = (long) 0;
            Float totalRevenueInMonth = (float) 0;
            List<BookDto> bookDtos = new ArrayList<>();
            for (Book book : bookRepository.findAll()) {
                BookDto bookDto = new BookDto();
                bookDto.setTitle(book.getTitle());
                bookDto.setSold(book.getSold());
                bookDto.setPrice(book.getPrice());
                bookDtos.add(bookDto);
                totalBooksInStock += book.getInStock();
                views += book.getView();
            }
            List<Bill> bills = billRepository.findAll();
            for (Bill bill : bills) {
                LocalDateTime billDateTime = LocalDateTime.ofInstant(bill.getTime().toInstant(),
                        ZoneId.systemDefault());
                int billMonth = billDateTime.getMonthValue();
                int billYear = billDateTime.getYear();
                if (billMonth == currentMonth && billYear == currentYear) {
                    totalTransactionInMonth += 1;
                    totalRevenueInMonth += bill.getTotalPrice();
                    for (Order order : bill.getOrders()) {
                        totalBooksSoldInMonth += order.getQuantity();
                        totalTheBookSoldInMonth += 1;
                    }
                }
                

            }
            ////// 4 first row card
            dashBoardDto.setTotalUser(userRepository.count() - 1);
            dashBoardDto.setTotalBooksInStock(totalBooksInStock);
            dashBoardDto.setTotalBooksSoldInMonth(totalBooksSoldInMonth);


            ////// Transaction Gauge
            dashBoardDto.setTotalTransactionInMonth(totalTransactionInMonth);
            dashBoardDto.setTotalTransactionKPI((long) 100);

            ////// Revenue Gauge
            dashBoardDto.setTotalRevenueInMonth(totalRevenueInMonth);
            dashBoardDto.setTotalRevenueKPI((float) 10000);

            ////// Transfer Gauge
            dashBoardDto.setView(views);
            dashBoardDto.setTotalTheBookSoldInMonth(totalTheBookSoldInMonth);



            // dashBoardDto.setBills(null);
            return dashBoardDto;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
}
