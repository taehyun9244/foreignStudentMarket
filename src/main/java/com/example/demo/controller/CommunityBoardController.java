package com.example.demo.controller;


import com.example.demo.dto.reponse.ComBoardDetailRes;
import com.example.demo.dto.reponse.Response;
import com.example.demo.dto.request.ComBoardPostReq;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommunityBoardController {

    private final CommunityService communityService;


    //커뮤니티 게시판 전체 조회
    @GetMapping("/communities")
    public Response getCommunityBoard(@RequestParam(value ="offset", defaultValue = "0") int offset,
                                      @RequestParam(value = "limit", defaultValue = "100")int limit){
        return communityService.getCommunityBoard(offset, limit);
    }

    //querydsl -> dto 전체 조회
    @GetMapping("/communitiesV2")
    public Response getCommunityBoardV2(Pageable pageable){
        return communityService.getCommunityBoardV2(pageable);
    }

    //커뮤니티 게시판 상세조회
    @GetMapping("/communitiesV2/{communityId}")
    public List<ComBoardDetailRes> getComBoardDetailV2(@PathVariable Long communityId){
        return communityService.getComBoardDetailV2(communityId);
    }

    //커뮤니티 게시판 작성
    @PostMapping("/communities")
    public void postComBoard(@RequestBody ComBoardPostReq postDto,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        communityService.postComBoard(postDto, userDetails);
    }

    //커뮤니티 게시판 수정
    @PutMapping("/communities/{communitiesId}")
    public void editCommunityBoard(@PathVariable Long communitiesId,
                                   @RequestBody ComBoardDetailRes detailDto,
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
