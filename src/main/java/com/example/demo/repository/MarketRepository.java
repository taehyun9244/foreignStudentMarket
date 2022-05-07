package com.example.demo.repository;

import com.example.demo.model.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Market, Long> {
}
