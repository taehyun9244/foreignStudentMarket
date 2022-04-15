package com.example.demo.service;

import com.example.demo.dto.request.CommentPostReq;
import com.example.demo.model.Comment;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.DeliveryBoardRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final DeliveryBoardRepository deliveryBoardRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(DeliveryBoardRepository deliveryBoardRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.deliveryBoardRepository = deliveryBoardRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    //DeliveryBoard 댓글 작성
    @Transactional
    public void creatDeliComment(UserDetailsImpl userDetails, CommentPostReq postReq, Long deliveryBoardId) {
        User user = userDetails.getUser();
        User findUser = userRepository.findByUsername(user.getUsername()).orElseThrow(
                ()-> new RuntimeException("회원가입을 해 주세요")
        );
        DeliveryBoard findBoardId = deliveryBoardRepository.findById(deliveryBoardId).orElseThrow(
                ()-> new RuntimeException("존재하지 않는 게시글입니다")
        );
            Comment comment = new Comment(postReq, findUser, findBoardId);
            commentRepository.save(comment);
    }
}
