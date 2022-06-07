package com.example.demo.controller;


import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.DeliCommentPostReq;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.DeliCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class DeliCommentController {
    private final DeliCommentService deliCommentService;

    //Delivery 댓글조회
    @GetMapping("/deliveryBoard/{deliveryBoardId}/comments")
    public Response getDeliComment(@PathVariable Long deliveryBoardId){
        return deliCommentService.getDeliComment(deliveryBoardId);
    }

    //dto
    @GetMapping("/deliveryBoard/{deliveryBoardId}/commentsV2")
    public Response getDeliCommentV2(@PathVariable Long deliveryBoardId, Pageable pageable){
        return deliCommentService.getDeliCommentV2(deliveryBoardId, pageable);
    }

    //entity
    @GetMapping("/deliveryBoard/{deliveryBoardId}/commentsV3")
    public Response getDeliCommentV3(@PathVariable Long deliveryBoardId, Pageable pageable){
        return deliCommentService.getDeliCommentV3(deliveryBoardId, pageable);
    }

    //Delivery 댓글작성
    @PostMapping("/deliveryBoards/{deliveryBoardId}/comments")
    public void creatDeliComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @RequestBody DeliCommentPostReq postReq,
                                 @PathVariable Long deliveryBoardId){
        deliCommentService.creatDeliComment(userDetails, postReq, deliveryBoardId);
    }

    //Delivery 댓글삭제
    @DeleteMapping("/deliveryBoards/{deliveryBoardId}/comments/{commentId}")
    public void deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              @PathVariable Long commentId,
                              @PathVariable Long deliveryBoardId){
        deliCommentService.deleteDeliComment(userDetails, commentId, deliveryBoardId);
    }


}
