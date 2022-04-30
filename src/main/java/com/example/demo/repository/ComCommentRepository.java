package com.example.demo.repository;

import com.example.demo.model.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComCommentRepository extends JpaRepository<CommunityComment, Long> {
   List<CommunityComment> findAllByCommunityBoardIdOrderByCreatedAtDesc(Long communityBoardId);
}
