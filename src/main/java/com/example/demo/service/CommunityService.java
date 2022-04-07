package com.example.demo.service;

import com.example.demo.dto.reponse.ComBoardDetailDto;
import com.example.demo.dto.reponse.ComBoardSimResDto;
import com.example.demo.dto.request.ComBoardPostDto;
import com.example.demo.model.CommunityBoard;
import com.example.demo.model.User;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommunityService(CommunityRepository communityRepository, UserRepository userRepository) {
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
    }


    //커뮤니티 게시판 전체조회
    public List<ComBoardSimResDto> getCommunityBoard() {
        List<CommunityBoard> communityBoardList = communityRepository.findAllByOrderByCreatedAtDesc();
        return ComBoardSimResDto.list(communityBoardList);
    }

    //커뮤니티 게시판 상세조회
    public ComBoardDetailDto getComBoardDetail(Long communityId) {
        CommunityBoard communityBoard = communityRepository.findById(communityId).orElseThrow(
                () -> new RuntimeException("유저를 찾을 수 없습니다.")
        );
        return ComBoardDetailDto.detail(communityBoard);

    }

    //커뮤니티 게시판 작성
    public void postComBoard(ComBoardPostDto postDto) {
       User findUsername = userRepository.findByUsername(postDto.getUsername()).orElseThrow(
               ()-> new IllegalArgumentException("가입된 유저가 아닙니다. ")
       );
       communityRepository.save(postDto.postComBoard(findUsername));
    }

    //커뮤니티 게시판 수정

    //커뮤니티 게시판 삭제
}
