package com.example.demo.repository;

import com.example.demo.model.DeliComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliCommentRepository extends JpaRepository<DeliComment, Long> {
    List<DeliComment> findByDeliveryBoardId(Long deliveryBoardId);
    List<DeliComment> findAllByDeliveryBoardIdOrderByCreatedAtDesc(Long deliveryBoardId);
}
