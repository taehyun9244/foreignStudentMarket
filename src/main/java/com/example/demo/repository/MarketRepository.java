package com.example.demo.repository;

import com.example.demo.model.MarketBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<MarketBoard, Long> {
}
