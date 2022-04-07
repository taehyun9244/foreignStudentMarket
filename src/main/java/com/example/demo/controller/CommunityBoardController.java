package com.example.demo.controller;


import com.example.demo.dto.reponse.ComBoardDetailDto;
import com.example.demo.dto.reponse.ComBoardSimResDto;
import com.example.demo.dto.request.ComBoardPostDto;
import com.example.demo.service.CommunityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class CommunityBoardController {

    private final CommunityService communityService;

    public CommunityBoardController(CommunityService communityService) {
        this.communityService = communityService;
    }

    //커뮤니티 게시판 전체 조회
    @GetMapping("/communities")
    public ResponseEntity<List<ComBoardSimResDto>> getCommunityBoard(){
        communityService.getCommunityBoard();
        return ResponseEntity.ok().body(communityService.getCommunityBoard());
    }

    //커뮤니티 게시판 상세조회
    @GetMapping("/communities/{communityId}")
    public ResponseEntity<ComBoardDetailDto> getComBoardDetail(@PathVariable Long communityId){
        communityService.getComBoardDetail(communityId);
        return ResponseEntity.ok().body(communityService.getComBoardDetail(communityId));
    }

    //커뮤니티 게시판 작성
    @PostMapping("/communities")
    public ResponseEntity<Void> postComBoard(@RequestBody ComBoardPostDto postDto){
        communityService.postComBoard(postDto);
        return ResponseEntity.created(URI.create("/communities")).build();
    }

    //커뮤니티 게시판 수정

    //커뮤니티 게시판 삭제

}
