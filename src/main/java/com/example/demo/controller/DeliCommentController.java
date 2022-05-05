package com.example.demo.controller;


import com.example.demo.dto.reponse.DeliCommentResDto;
import com.example.demo.dto.reponse.ResultList;
import com.example.demo.dto.request.DeliCommentPostReq;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.DeliCommentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class DeliCommentController {
    private final DeliCommentService deliCommentService;

    public DeliCommentController(DeliCommentService deliCommentService) {
        this.deliCommentService = deliCommentService;
    }

    //Delivery 댓글조회
    @GetMapping("/deliveryBoard/{deliveryBoardId}/comments")
    public ResultList getDeliComment(@PathVariable Long deliveryBoardId){
        return deliCommentService.getDeliComment(deliveryBoardId);
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
