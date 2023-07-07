package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Paid;


public interface PaidRepository extends JpaRepository<Paid, Long>{
}
