package com.example.demo.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OrderService;

import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class Dashboard {
    private UserRepository userRepository;
    private BookRepository bookRepository;
    private BillRepository billRepository;
    private ReviewRepository reviewRepository;
    private OrderRepository orderRepository;
    private OrderService orderService;

    @GetMapping("/dashboard")
    public DashBoardDto dashboard() {
        try {
            LocalDateTime now = LocalDateTime.now();
            int currentMonth = now.getMonthValue();
            int currentYear = now.getYear();
            int previousMonth = currentMonth == 1 ? 12 : currentMonth - 1;
            int previousYear = currentMonth == 1 ? currentYear - 1 : currentYear;
            DashBoardDto dashBoardDto = new DashBoardDto();
            Long totalBooksInStock = (long) 0;
            // Float totalRevenue = (float) 0;
            Long totalBooksSoldInMonth = (long) 0;
            Long views = (long) 0;
            Long totalTheBookSoldInMonth = (long) 0;
            Long totalTransactionInMonth = (long) 0;
            Float totalRevenueInMonth = (float) 0;
            Float totalRevenueInPreMonth = (float) 0;
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
            Float[] revenueDetail = new Float[24];
            Arrays.fill(revenueDetail, 0.0f);
            Map<Long, Float> revenueDetailMap = new HashMap<>();
            List<Bill> bills = billRepository.findAll();
            for (Bill bill : bills) {
                LocalDateTime billDateTime = LocalDateTime.ofInstant(bill.getTime().toInstant(),
                        ZoneId.systemDefault());
                int billDay = billDateTime.getDayOfMonth();
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

                if (billMonth == previousMonth && billYear == previousYear) {
                    totalRevenueInPreMonth += bill.getTotalPrice();
                }
                if (billDay >= 1 && billDay <= 15) {
                    revenueDetail[billMonth * 2 - 2] += bill.getTotalPrice();
                } else {
                    revenueDetail[billMonth * 2 - 1] += bill.getTotalPrice();
                }
            }
            for (int i = 0; i < revenueDetail.length; i++) {
                revenueDetailMap.put((long) i * 5, revenueDetail[i]);
            }
            String maxRevenue = String.valueOf(Math.round(Collections.max(Arrays.asList(revenueDetail))));

            Long firstNum = Long.parseLong(maxRevenue.substring(0, 1));
            firstNum += 1;
            maxRevenue = firstNum.toString()
                    + String.join("", Collections.nCopies(maxRevenue.substring(1).length(), "0"));
            Map<Long, Long> revenuesConfig = new HashMap<>();
            for (int i = 0; i < 6; i++) {
                revenuesConfig.put((long) i * 20, Long.parseLong(maxRevenue) / 5 * i);
            }
            ////// 4 first row card
            dashBoardDto.setTotalUser(userRepository.count() - 1);
            dashBoardDto.setTotalBooksInStock(totalBooksInStock);
            dashBoardDto.setTotalBooksSoldInMonth(totalBooksSoldInMonth);
            dashBoardDto.setRevenueGrowthRate(
                    (totalRevenueInMonth - totalRevenueInPreMonth) / totalRevenueInPreMonth * 100);

            ////// Transaction Gauge
            dashBoardDto.setTotalTransactionInMonth(totalTransactionInMonth);
            dashBoardDto.setTotalTransactionKPI((long) 50);

            ////// Revenue Gauge
            dashBoardDto.setTotalRevenueInMonth(totalRevenueInMonth);
            dashBoardDto.setTotalRevenueKPI((float) 2000);

            ////// Transfer Gauge
            dashBoardDto.setView(views);
            dashBoardDto.setTotalTheBookSoldInMonth(totalTheBookSoldInMonth);

            ///// 3 newset orer
            dashBoardDto.setNewestOrders(orderService.getTop3NewestOrder());
            // dashBoardDto.setBills(null);

            ///// line chart
            dashBoardDto.setRevenuesDetail(revenueDetailMap);
            dashBoardDto.setRevenues(revenuesConfig);
            return dashBoardDto;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
}
