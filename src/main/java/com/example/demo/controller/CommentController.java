//package com.example.demo.controller;
//
//import com.example.demo.dto.reponse.CommentResDto;
//import com.example.demo.model.Comment;
//import com.example.demo.service.CommentService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//
//@RestController
//public class CommentController {
//    private final CommentService commentService;
//
//    public CommentController(CommentService commentService) {
//        this.commentService = commentService;
//    }
//
//    @GetMapping("/communtiyBoards/{communityId}comments")
//    public ResponseEntity<List<Comment>> getBoardComment(@PathVariable  CommentResDto commentResDto){
//        commentService.getBoardCommentList();
//        return new ResponseEntity<>
//    }
//}
