package com.example.demo.service;

import com.example.demo.dto.reponse.DeliCommentRes;
import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.DeliCommentPostReq;
import com.example.demo.model.DeliComment;
import com.example.demo.model.DeliveryBoard;
import com.example.demo.model.User;
import com.example.demo.repository.DeliCommentRepository;
import com.example.demo.repository.DeliveryBoardRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.queryRepository.CommentQueryRepository;
import com.example.demo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DeliCommentService {

    private final DeliveryBoardRepository deliveryBoardRepository;
    private final DeliCommentRepository deliCommentRepository;
    private final CommentQueryRepository queryRepository;
    private final UserRepository userRepository;


    //DeliveryBoard 댓글 조회
    @Transactional(readOnly = true)
    public Response getDeliComment(Long deliveryBoardId){
        List<DeliComment> findDeliBoardComment = deliCommentRepository.findAllByDeliveryBoardIdOrderByCreatedAtDesc(deliveryBoardId);
        List<DeliCommentRes> resDtos = findDeliBoardComment.stream()
                .map(deliComment -> new DeliCommentRes(deliComment))
                .collect(toList());
        return new Response<>(resDtos);
    }

    //DeliveryBoard 댓글 작성
    public void creatDeliComment(UserDetailsImpl userDetails, DeliCommentPostReq postReq, Long deliveryBoardId) {
        User user = userDetails.getUser();
        User writer= userRepository.findByUsername(user.getUsername()).orElseThrow(
                ()-> new RuntimeException("회원가입을 해 주세요")
        );
        DeliveryBoard existBoardId = deliveryBoardRepository.findById(deliveryBoardId).orElseThrow(
                ()-> new RuntimeException("존재하지 않는 게시글입니다")
        );
        List<DeliComment> countComment = deliCommentRepository.findByDeliveryBoardId(deliveryBoardId);
        int deliCommentSize = countComment.size();
        DeliComment deliComment = new DeliComment(postReq, writer, existBoardId);
        deliComment.getDeliveryBoard().addComment(deliCommentSize);
        deliCommentRepository.save(deliComment);
    }

    //DeliveryBoard 댓글 삭제
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
            deliveryBoard.removeComment(deliCommentSize);
            deliCommentRepository.delete(findDeliComment);
        }else throw new RuntimeException("댓글 작성자가 아닙니다");
    }
}
