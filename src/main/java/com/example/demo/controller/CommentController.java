package com.example.demo.controller;


import com.example.demo.dto.request.CommentPostReq;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.CommentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/deliveryBoards/{deliveryBoardId}/comments/")
    public void creatDeliComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @RequestBody CommentPostReq postReq,
                                 @PathVariable Long deliveryBoardId){
        commentService.creatDeliComment(userDetails, postReq, deliveryBoardId);
    }

//    @PostMapping("/comments/{communityBoardId}")
//    public void creatComComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
//                                @RequestBody CommentPostReq postReq,
//                                @PathVariable Long communityBoardId){
//        commentService.creatComComment(userDetails, postReq, communityBoardId);
//    }
//
//    @DeleteMapping("/comments/{commentId}")
//    public void deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
//                              @PathVariable Long commentId){
//        commentService.deleteDeliComment(userDetails, commentId);
//    }


}
