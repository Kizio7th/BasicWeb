package com.example.demo.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.entity.Bill;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashBoardDto {
    private Long totalUser;
    private Long totalBooksInStock;
    private Long totalBooksSoldInMonth;
    private List<Bill> bills;
    private Long View;
    private Long totalTheBookSoldInMonth;
    private Float totalRevenueInMonth;
    private Float totalRevenueKPI;
    private Long totalTransactionInMonth;
    private Long totalTransactionKPI;
    private Float revenueGrowthRate;
    private List<OrderDto> newestOrders;
    private Map<Long, Long> revenues = new HashMap<Long, Long>();
    private Map<Long, Float> revenuesDetail;
}
