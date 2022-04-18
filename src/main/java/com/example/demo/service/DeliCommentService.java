package com.example.demo.service;

import com.example.demo.dto.reponse.DeliCommentResDto;
import com.example.demo.dto.request.DeliCommentPostReq;
import com.example.demo.model.DeliComment;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.repository.DeliCommentRepository;
import com.example.demo.repository.DeliveryBoardRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliCommentService {

    private final DeliveryBoardRepository deliveryBoardRepository;
    private final DeliCommentRepository deliCommentRepository;
    private final UserRepository userRepository;

    @Autowired
    public DeliCommentService(DeliveryBoardRepository deliveryBoardRepository, DeliCommentRepository deliCommentRepository, UserRepository userRepository) {
        this.deliveryBoardRepository = deliveryBoardRepository;
        this.deliCommentRepository = deliCommentRepository;
        this.userRepository = userRepository;
    }

    //DeliveryBoard 댓글 조회
    @Transactional(readOnly = true)
    public List<DeliCommentResDto> getDeliComment(Long deliveryBoardId){
        List<DeliComment> selectAllComment = deliCommentRepository.findAllByDeliveryBoardIdOrderByCreatedAtDesc(deliveryBoardId);
        List<DeliCommentResDto> resDtos = new ArrayList<>();
        for (DeliComment deliComments : selectAllComment){
            resDtos.add(new DeliCommentResDto(deliComments));
        }return resDtos;
    }

    //DeliveryBoard 댓글 작성
    @Transactional
    public void creatDeliComment(UserDetailsImpl userDetails, DeliCommentPostReq postReq, Long deliveryBoardId) {
        User user = userDetails.getUser();
        User writer= userRepository.findByUsername(user.getUsername()).orElseThrow(
                ()-> new RuntimeException("회원가입을 해 주세요")
        );
        DeliveryBoard existBoard = deliveryBoardRepository.findById(deliveryBoardId).orElseThrow(
                ()-> new RuntimeException("존재하지 않는 게시글입니다")
        );
        List<DeliComment> countComment = deliCommentRepository.findByDeliveryBoardId(deliveryBoardId);
        int deliCommentSize = countComment.size();
        existBoard.setCountComment(deliCommentSize +1);
        DeliComment deliComment = new DeliComment(postReq, writer, existBoard);
        deliCommentRepository.save(deliComment);
    }

    //DeliveryBoard 댓글 삭제
    @Transactional
    public void deleteDeliComment(UserDetailsImpl userDetails, Long commentId, Long deliveryBoardId) {
        User user = userDetails.getUser();
        DeliComment findDeliComment = deliCommentRepository.findById(commentId).orElseThrow(
                ()-> new RuntimeException("존재하지 않는 댓글입니다")
        );
        DeliveryBoard deliveryBoard = deliveryBoardRepository.findById(deliveryBoardId).orElseThrow(
                ()-> new RuntimeException("존재하지 않는 게시글입니다")
        );
        if (user.getUsername().equals(findDeliComment.getUser().getUsername())){
            List<DeliComment> countComment = deliCommentRepository.findByDeliveryBoardId(deliveryBoardId);
            int deliCommentSize = countComment.size();
            deliveryBoard.setCountComment(deliCommentSize -1);
            deliCommentRepository.delete(findDeliComment);
        }else throw new RuntimeException("댓글 작성자가 아닙니다");
    }
}
