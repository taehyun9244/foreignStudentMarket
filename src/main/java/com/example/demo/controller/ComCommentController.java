//package com.example.demo.controller;
//
//import com.example.demo.dto.reponse.ComCommentResDto;
//import com.example.demo.dto.request.ComCommentPostReq;
//import com.example.demo.security.UserDetailsImpl;
//import com.example.demo.service.ComCommentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/communityBoard/{communityBoardId}/comComments")
//public class ComCommentController {
//
//    private final ComCommentService comCommentService;
//
//    //Delivery 댓글조회
//    @GetMapping
//    public List<ComCommentResDto> getComComment(@PathVariable Long communityBoardId){
//        return comCommentService.getComComment(communityBoardId);
//    }
//
//    //Delivery 댓글작성
//    @PostMapping
//    public void creatComComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
//                                 @RequestBody ComCommentPostReq postReq,
//                                 @PathVariable Long communityBoardId){
//        comCommentService.creatComComment(userDetails, postReq, communityBoardId);
//    }
//
//    //Delivery 댓글삭제
//    @DeleteMapping("{commentId}")
//    public void deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
//                              @PathVariable Long comCommentId,
//                              @PathVariable Long communityBoardId){
//        comCommentService.deleteComComment(userDetails, comCommentId, communityBoardId);
//    }
//}
