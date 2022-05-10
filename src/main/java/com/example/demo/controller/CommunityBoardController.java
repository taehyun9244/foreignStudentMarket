package com.example.demo.controller;


import com.example.demo.dto.reponse.ComBoardDetailDto;
import com.example.demo.dto.reponse.ComBoardSimResDto;
import com.example.demo.dto.reponse.ResultList;
import com.example.demo.dto.request.ComBoardPostDto;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommunityBoardController {

    private final CommunityService communityService;


    //커뮤니티 게시판 전체 조회
    @GetMapping("/communities")
    public ResultList getCommunityBoard(@RequestParam(value ="offset", defaultValue = "0") int offset,
                                        @RequestParam(value = "limit", defaultValue = "100")int limit){
        return communityService.getCommunityBoard(offset, limit);
    }

    //커뮤니티 게시판 상세조회
    @GetMapping("/communities/{communityId}")
    public ComBoardDetailDto getComBoardDetail(@PathVariable Long communityId){
        return communityService.getComBoardDetail(communityId);
    }

    //커뮤니티 게시판 작성
    @PostMapping("/communities")
    public void postComBoard(@RequestBody ComBoardPostDto postDto,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        communityService.postComBoard(postDto, userDetails);
    }

    //커뮤니티 게시판 수정
    @PutMapping("/communities/{communitiesId}")
    public void editCommunityBoard(@PathVariable Long communitiesId,
                                   @RequestBody ComBoardDetailDto detailDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails){
        communityService.editCommunityBoard(communitiesId, detailDto, userDetails);
    }

    //커뮤니티 게시판 삭제
    @DeleteMapping("/communities/{communitiesId}")
    public void deleteComBoard(@PathVariable Long communitiesId,
                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        communityService.deleteComBoard(communitiesId, userDetails);
    }

}
