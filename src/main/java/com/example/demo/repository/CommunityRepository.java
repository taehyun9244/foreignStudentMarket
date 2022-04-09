package com.example.demo.repository;

import com.example.demo.model.CommunityBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface CommunityRepository extends JpaRepository<CommunityBoard, Long>{
    List<CommunityBoard> findAllByOrderByCreatedAtDesc();

}
