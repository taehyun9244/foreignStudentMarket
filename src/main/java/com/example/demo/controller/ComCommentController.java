package com.example.demo.controller;

import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.ComCommentPostReq;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.ComCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communityBoard/{communityBoardId}/comComments")
public class ComCommentController {

    private final ComCommentService comCommentService;

    //Community 댓글조회
    @GetMapping
    public Response getComCommentV2(Pageable pageable){
        return comCommentService.getComCommentV2(pageable);
    }

    //Community 댓글작성
    @PostMapping
    public void creatComComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @RequestBody ComCommentPostReq postReq,
                                 @PathVariable Long communityBoardId){
        comCommentService.creatComComment(userDetails, postReq, communityBoardId);
    }

    //Community 댓글삭제
    @DeleteMapping("{commentId}")
    public void deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              @PathVariable Long comCommentId,
                              @PathVariable Long communityBoardId){
        comCommentService.deleteComComment(userDetails, comCommentId, communityBoardId);
    }
}
