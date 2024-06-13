package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.Bill;
import com.example.demo.entity.Paid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashBoardDto {
    private Long userCount;
    private Long bookCount;
    private Long boughtCount;
    private Long paidCount;
    private Float totalRevenue;
    private List<Paid> paids;
    private List<BookDto> books;
    private List<Bill> bills;
}
