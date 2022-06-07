package com.example.demo.repository;

import com.example.demo.model.DeliveryBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryBoardRepository extends JpaRepository<DeliveryBoard, Long>{
    List<DeliveryBoard> findAllByOrderByCreatedAtDesc();
}
